package io.github.Mishaa105.price_tracker.service;

import io.github.Mishaa105.price_tracker.batch.ProductBatch;
import io.github.Mishaa105.price_tracker.constants.GraphQlCategoryGridConstants;
import io.github.Mishaa105.price_tracker.db.entity.*;
import io.github.Mishaa105.price_tracker.db.entity.Currency;
import io.github.Mishaa105.price_tracker.db.repository.*;
import io.github.Mishaa105.price_tracker.dto.allproducts.AllProducts;
import io.github.Mishaa105.price_tracker.dto.allproducts.Concept;
import io.github.Mishaa105.price_tracker.dto.genre.ProductGenresResponse;
import io.github.Mishaa105.price_tracker.dto.language.LanguagesDataResponse;
import io.github.Mishaa105.price_tracker.dto.metadata.ProductMetadataResponse;
import io.github.Mishaa105.price_tracker.dto.graphql.*;
import io.github.Mishaa105.price_tracker.dto.media.ProductMediaResponse;
import io.github.Mishaa105.price_tracker.dto.product.Local;
import io.github.Mishaa105.price_tracker.dto.product.PlayStationProductResponse;
import io.github.Mishaa105.price_tracker.dto.product.SkuPriceDetail;
import io.github.Mishaa105.price_tracker.enums.graphql.GenreEnum;
import io.github.Mishaa105.price_tracker.enums.graphql.PlatformEnum;
import io.github.Mishaa105.price_tracker.enums.graphql.ProductTypeEnum;
import io.github.Mishaa105.price_tracker.enums.graphql.SortByEnum;
import io.github.Mishaa105.price_tracker.enums.regions.Regions;
import io.github.Mishaa105.price_tracker.infrastructure.JsoupClient;
import io.github.Mishaa105.price_tracker.infrastructure.RestApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataBaseUpdateService
{
    private final ObjectMapper mapper;
    private final JsoupClient jsoupClient;
    private final ProductPageHtmlParser productPageParser;
    private final ProductMediaHtmlParser productMediaParser;
    private final ProductMetadataHtmlParser productMetadataParser;
    private final ProductLanguagesHtmlParser languagesParser;
    private final ProductGenresHtmlParser genresParser;
    private final ProductRepository productRepository;
    private final PriceRepository allPriceRepository;
    private final CurrentPriceRepository currentPriceRepository;
    private final OfferRepository offerRepository;
    private final PlatformRepository platformRepository;
    private final StoreClassificationRepository storeClassificationRepository;
    private final PublisherRepository publisherRepository;
    private final BrandRepository brandRepository;
    private final CurrencyRepository currencyRepository;
    private final LanguageRepository languageRepository;
    private final GenreRepository genreRepository;
    private final RestApiClient restApiClient;

    private List<String> getListOfProductsId(String rawJson)
    {
        AllProducts batch = mapper.readValue(rawJson, AllProducts.class);
        List<Concept> listOfProduct = batch.data().categoryGridRetrieve().getListOfProducts();
        List<String> listOfId = new ArrayList<>();
        for (Concept product : listOfProduct)
        {
            if(product.getId() != null)
            {
                listOfId.addAll(product.getId());
            }
        }
        log.info("Лист с {} айди получен", listOfId.size());
        log.debug("Лист айди: {}", listOfId);
        return listOfId;
    }

    private Document getHtmlDoc(String region, String id)
    {
        return jsoupClient.loadHtmlPage(region, JsoupClient.RequestType.PRODUCT, id);
    }

    private PlayStationProductResponse getProductData(Document htmlDoc)
    {
        String jsonData = productPageParser.extractJson(htmlDoc);
        log.info("Данные о продукте получены");
        return productPageParser.deserialize(jsonData);
    }

    private ProductMediaResponse getProductMediaData(Document htmlDoc)
    {
        String jsonData = productMediaParser.extractJson(htmlDoc);
        log.info("Данные о медиа и жанрах получены");
        return productMediaParser.deserialize(jsonData);
    }

    private ProductMetadataResponse getProductMetadata(Document htmlDoc)
    {
        String jsonData = productMetadataParser.extractJson(htmlDoc);
        log.info("Метаданные о продукте получены");
        return productMetadataParser.deserialize(jsonData);
    }

    private LanguagesDataResponse getProductLanguagesData(Document htmlDoc)
    {
        String jsonData = languagesParser.extractJson(htmlDoc);
        log.info("Данные о локализации получены");
        return languagesParser.deserialize(jsonData);
    }

    private ProductGenresResponse getProductGenresData(Document htmlDoc)
    {
        String jsonData = genresParser.extractJson(htmlDoc);
        log.info("Данные о жанрах получены");
        return genresParser.deserialize(jsonData);
    }

    private ProductBatch buildDatabaseEntities(PlayStationProductResponse productData, ProductMediaResponse mediaResponse,
                                               ProductMetadataResponse productMetadata, LanguagesDataResponse languageData,
                                               ProductGenresResponse genresResponse)
    {
        List<Local> localList = productData.getListOfAvailablePriceData();
        ProductBatch batch = new ProductBatch();

        for (Local local : localList)
        {
            for(SkuPriceDetail price : local.telemetryMeta().skuDetail().skuPriceDetail())
            {
                String name = productData.getName();
                String invariantName = productData.getInvariantName();
                String productId = productData.args().productId();
                Integer basePrice = price.originalPriceValue();
                Integer discountPriceValue = price.discountPriceValue();
                String offerBranding = price.offerBranding();
                String offerAvailability = local.offerAvailability();
                String priceCurrencyCode = price.priceCurrencyCode();
                String description = productMetadata.getLongDescription();
                String edition = productMetadata.getEdition();
                String productType = productMetadata.getStoreDisplayClassification();
                String releaseDate = productMetadata.getReleaseDate();
                String publisherName = productMetadata.getPublisherName();
                Double averageRating = productMetadata.getAverageRating();
                Integer ratingsCount = productMetadata.getTotalRatingsCount();
                String previewUrl = mediaResponse.getPreviewUrl();
                Set<String> genreNames = genresResponse.getGenres();

                Product product = new Product(productId, name, invariantName, previewUrl, description, edition, releaseDate, averageRating, ratingsCount);
                Offer offer = new Offer(null, null, offerAvailability);
                Set<Platform> platforms = productMetadata.getPlatforms().stream().map(Platform::new).collect(Collectors.toSet());
                Set<Language> languages = new HashSet<>();

                for (Platform platform : platforms)
                {
                   Set<String> spokenLanguages = languageData.getSpokenLanguagesByPlatform(platform);
                   Set<String> screenLanguages = languageData.getScreenLanguagesByPlatform(platform);

                   if(spokenLanguages != null)
                   {
                       for (String lang : spokenLanguages)
                       {
                           Language language = new Language(lang, "SPOKEN");
                           languages.add(language);
                           batch.getLanguages().add(language);
                       }
                   }

                    if(screenLanguages != null)
                    {
                        for (String lang : screenLanguages)
                        {
                            Language language = new Language(lang, "SCREEN");
                            languages.add(language);
                            batch.getLanguages().add(language);
                        }
                    }

                }

                Set<Genre> genres = genreNames.stream().map(Genre::new).collect(Collectors.toSet());

                CurrentPrice currentPrice = new CurrentPrice(basePrice, discountPriceValue);
                Price allPrices = new Price(basePrice, discountPriceValue);
                Publisher publisher = new Publisher(publisherName);
                StoreClassification storeClassification = new StoreClassification(productType);
                Brand brands = new Brand(offerBranding);
                Currency currencies = new Currency(priceCurrencyCode);

                currentPrice.setProduct(product);
                currentPrice.setOffer(offer);
                currentPrice.setPriceCurrencyCode(currencies);
                currentPrice.setOfferBrand(brands);
                allPrices.setProduct(product);
                allPrices.setOffer(offer);
                allPrices.setPriceCurrencyCode(currencies);
                allPrices.setOfferBrand(brands);
                product.setPublisherName(publisher);
                product.setStoreClassification(storeClassification);
                product.setPlatforms(platforms);
                product.setLanguages(languages);
                product.setGenres(genres);

                batch.getProducts().add(product);
                batch.getOffers().add(offer);
                batch.getCurrentPrices().add(currentPrice);
                batch.getPrices().add(allPrices);
                batch.getPlatforms().addAll(platforms);
                batch.getGenres().addAll(genres);
                batch.getPublishers().add(publisher);
                batch.getStoreClassifications().add(storeClassification);
                batch.getCurrencies().add(currencies);
                batch.getBrands().add(brands);
            }
        }
        log.info("Батч сформирован");
        return batch;
    }

    private void saveBatchToDb(ProductBatch batch)
    {
        storeClassificationRepository.saveAll(batch.getStoreClassifications());
        publisherRepository.saveAll(batch.getPublishers());
        platformRepository.saveAll(batch.getPlatforms());
        languageRepository.saveAll(batch.getLanguages());
        genreRepository.saveAll(batch.getGenres());
        productRepository.saveAll(batch.getProducts());
        brandRepository.saveAll(batch.getBrands());
        currencyRepository.saveAll(batch.getCurrencies());
        offerRepository.saveAll(batch.getOffers());
        currentPriceRepository.saveAll(batch.getCurrentPrices());
        allPriceRepository.saveAll(batch.getPrices());
        log.info("Батч сохранен в БД");
    }

    private String buildGraphQlRequestUrl(ProductTypeEnum productType, PlatformEnum platform, SortByEnum sortType,
                                          GenreEnum genre, int size, int offset)
    {
        String baseUrl = GraphQlCategoryGridConstants.BASE_URL;
        String operationName = GraphQlCategoryGridConstants.OPERATION_NAME;
        int version = GraphQlCategoryGridConstants.VERSION;
        String hash = GraphQlCategoryGridConstants.HASH;

        PageArgs pageArgs = new PageArgs(size, offset);
        SortBy sortBy = new SortBy(sortType.getSortType(), sortType.isAscending());
        List<String> filterBy = List.of(genre.getGenre(), platform.getPlatform());
        List<String> facetOptions = Collections.emptyList();

        PersistedQuery persistedQuery = new PersistedQuery(version, hash);

        Variables variables = new Variables(productType.getId(), pageArgs, sortBy, filterBy, facetOptions);
        Extensions extensions = new Extensions(persistedQuery);

        String variablesJson = mapper.writeValueAsString(variables);
        String extensionsJson = mapper.writeValueAsString(extensions);

        String encodedVariables =
                URLEncoder.encode(variablesJson, StandardCharsets.UTF_8);

        String encodedExtensions =
                URLEncoder.encode(extensionsJson, StandardCharsets.UTF_8);
        String url = baseUrl + "?operationName=" + operationName + "&variables=" + encodedVariables + "&extensions=" + encodedExtensions;
        log.debug("GraphQL URL: {}", url);
        return url;
    }

    public void bdSaveTest()
    {
        for (int i = 0; i <50; i++)
        {
            String url = buildGraphQlRequestUrl(ProductTypeEnum.PS5, PlatformEnum.PS5, SortByEnum.BESTSELLING, GenreEnum.ADVENTURE, 51, i);
            // NULL HANDLER
            String rawJson = restApiClient.getData(url, Regions.US.getLocaleHeaderCode());
            List<String> list = getListOfProductsId(rawJson);
            String id = list.get(3);
            Document htmlDoc = getHtmlDoc(Regions.US.getRegionCode(), id);
            PlayStationProductResponse productResponse = getProductData(htmlDoc);
            ProductMetadataResponse metadataResponse = getProductMetadata(htmlDoc);
            LanguagesDataResponse languagesDataResponse = getProductLanguagesData(htmlDoc);
            ProductMediaResponse mediaResponse = getProductMediaData(htmlDoc);
            ProductGenresResponse genresResponse = getProductGenresData(htmlDoc);
            ProductBatch batch = buildDatabaseEntities(productResponse, mediaResponse, metadataResponse, languagesDataResponse, genresResponse);
            saveBatchToDb(batch);
        }
    }
}
