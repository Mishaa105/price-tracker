package io.github.Mishaa105.price_tracker.infrastructure.batch;

import io.github.Mishaa105.price_tracker.dto.product.ProductResponse;
import io.github.Mishaa105.price_tracker.dto.product.offer.Price;
import io.github.Mishaa105.price_tracker.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BatchBuilder
{
    public ProductBatch buildBatch(ProductResponse productData)
    {
        List<Price> priceList = productData.offerResponse().getListOfAvailablePriceData();
        ProductBatch batch = new ProductBatch();

        for (Price price : priceList)
        {
            String name = productData.offerResponse().getName();
            String invariantName = productData.offerResponse().getInvariantName();
            String productId = productData.offerResponse().args().productId();
            Integer basePrice = price.basePriceValue();
            Integer discountPriceValue = price.discountedValue();
            String offerBranding = price.membershipType();
            String offerAvailability = price.endTime();
            String priceCurrencyCode = price.currencyCode();
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
            Offer offer = new Offer(null, null, offerAvailability);
            Set<Platform> platforms = productData.metadataResponse().getPlatforms().stream().map(Platform::new).collect(Collectors.toSet());
            Set<Language> languages = new HashSet<>();

            for (Platform platform : platforms)
            {
                Set<String> spokenLanguages = productData.languagesResponse().getSpokenLanguagesByPlatform(platform);
                Set<String> screenLanguages = productData.languagesResponse().getScreenLanguagesByPlatform(platform);

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
            io.github.Mishaa105.price_tracker.entity.Price allPrices = new io.github.Mishaa105.price_tracker.entity.Price(basePrice, discountPriceValue);
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
        log.info("Батч сформирован");
        return batch;
    }
}
