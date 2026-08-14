package io.github.Mishaa105.price_tracker.infrastructure.batch;

import io.github.Mishaa105.price_tracker.dto.product.ProductResponse;
import io.github.Mishaa105.price_tracker.dto.product.offer.PriceDto;
import io.github.Mishaa105.price_tracker.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BatchBuilder
{
    public List<ProductAggregate> buildBatch(List<ProductResponse> productsData)
    {
        List<ProductAggregate> batch = new ArrayList<>();

        for (ProductResponse productData : productsData)
        {
            ProductAggregate aggregate = new ProductAggregate();

            String name = productData.offerResponse().getName();
            String invariantName = productData.offerResponse().getInvariantName();
            String productId = productData.offerResponse().args().productId();
            String description = productData.metadataResponse().getLongDescription();
            String edition = productData.metadataResponse().getEdition();
            String productType = productData.metadataResponse().getStoreDisplayClassification();
            String releaseDate = productData.metadataResponse().getReleaseDate();
            String publisherName = productData.metadataResponse().getPublisherName();
            Double averageRating = productData.metadataResponse().getAverageRating();
            Integer ratingsCount = productData.metadataResponse().getTotalRatingsCount();
            String previewUrl = productData.mediaResponse().getPreviewUrl();
            Set<String> genreNames = productData.genresResponse().getGenres();

            Product product = new Product(productId, name, invariantName, previewUrl, description, edition, releaseDate, averageRating, ratingsCount);
            StoreClassification storeClassification = new StoreClassification(productType);
            Publisher publisher = new Publisher(publisherName);

            Set<Platform> platforms = productData.metadataResponse().getPlatforms().stream().map(Platform::new).collect(Collectors.toSet());

            Set<Language> languages = new HashSet<>();

            for (Platform platform : platforms)
            {
                Set<String> spokenLanguages = productData.languagesResponse().getSpokenLanguagesByPlatform(platform);
                Set<String> screenLanguages = productData.languagesResponse().getScreenLanguagesByPlatform(platform);

                if (spokenLanguages != null)
                {
                    for (String lang : spokenLanguages)
                    {
                        Language language = new Language(lang, "SPOKEN");
                        languages.add(language);
                        aggregate.getLanguages().add(language);
                    }
                }

                if (screenLanguages != null)
                {
                    for (String lang : screenLanguages)
                    {
                        Language language = new Language(lang, "SCREEN");
                        languages.add(language);
                        aggregate.getLanguages().add(language);
                    }
                }
            }

            Set<Genre> genres = genreNames.stream().map(Genre::new).collect(Collectors.toSet());

            product.setPublisherName(publisher);
            product.setStoreClassification(storeClassification);
            product.setPlatforms(platforms);
            product.setLanguages(languages);
            product.setGenres(genres);

            aggregate.getProducts().add(product);
            aggregate.getPlatforms().addAll(platforms);
            aggregate.getGenres().addAll(genres);
            aggregate.getPublishers().add(publisher);
            aggregate.getStoreClassifications().add(storeClassification);

            List<PriceDto> priceList = productData.offerResponse().getListOfAvailablePriceData();

            for (PriceDto price : priceList)
            {

                Integer basePrice = price.basePriceValue();
                Integer discountPriceValue = price.discountedValue();
                String offerBranding = price.membershipType();
                String offerAvailability = price.endTime();
                String priceCurrencyCode = price.currencyCode();

                CurrentPrice currentPrice = new CurrentPrice(basePrice, discountPriceValue);
                Price allPrices = new Price(basePrice, discountPriceValue);
                Brand brands = new Brand(offerBranding);
                Currency currencies = new Currency(priceCurrencyCode);
                Offer offer = new Offer(null, null, offerAvailability);

                currentPrice.setProduct(product);
                currentPrice.setOffer(offer);
                currentPrice.setPriceCurrencyCode(currencies);
                currentPrice.setOfferBrand(brands);

                allPrices.setProduct(product);
                allPrices.setOffer(offer);
                allPrices.setPriceCurrencyCode(currencies);
                allPrices.setOfferBrand(brands);

                aggregate.getOffers().add(offer);
                aggregate.getCurrentPrices().add(currentPrice);
                aggregate.getPrices().add(allPrices);
                aggregate.getCurrencies().add(currencies);
                aggregate.getBrands().add(brands);
            }

            batch.add(aggregate);
        }
        log.info("Батч сформирован");
        return batch;
    }
}
