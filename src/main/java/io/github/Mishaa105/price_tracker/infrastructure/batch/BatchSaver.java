package io.github.Mishaa105.price_tracker.infrastructure.batch;

import io.github.Mishaa105.price_tracker.entity.*;
import io.github.Mishaa105.price_tracker.infrastructure.db.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class BatchSaver
{
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

    @Transactional
    public void saveBatchToDb(List<ProductAggregate> batch)
    {
        Set<StoreClassification> allClassifications = new HashSet<>();
        Set<Publisher> allPublishers = new HashSet<>();
        Set<Platform> allPlatforms = new HashSet<>();
        Set<Language> allLanguages = new HashSet<>();
        Set<Genre> allGenres = new HashSet<>();
        Set<Brand> allBrands = new HashSet<>();
        Set<Currency> allCurrencies = new HashSet<>();

        List<Product> allProducts = new ArrayList<>();
        List<Offer> allOffers = new ArrayList<>();
        List<CurrentPrice> allCurrentPrices = new ArrayList<>();
        List<Price> allPrices = new ArrayList<>();

        for (ProductAggregate product : batch)
        {
            allClassifications.addAll(product.getStoreClassifications());
            allPublishers.addAll(product.getPublishers());
            allPlatforms.addAll(product.getPlatforms());
            allLanguages.addAll(product.getLanguages());
            allGenres.addAll(product.getGenres());
            allBrands.addAll(product.getBrands());
            allCurrencies.addAll(product.getCurrencies());
            allProducts.addAll(product.getProducts());
            allOffers.addAll(product.getOffers());
            allCurrentPrices.addAll(product.getCurrentPrices());
            allPrices.addAll(product.getPrices());
        }

        storeClassificationRepository.saveAll(allClassifications);
        publisherRepository.saveAll(allPublishers);
        platformRepository.saveAll(allPlatforms);
        languageRepository.saveAll(allLanguages);
        genreRepository.saveAll(allGenres);
        productRepository.saveAll(allProducts);
        brandRepository.saveAll(allBrands);
        currencyRepository.saveAll(allCurrencies);
        offerRepository.saveAll(allOffers);
        currentPriceRepository.saveAll(allCurrentPrices);
        allPriceRepository.saveAll(allPrices);
        log.info("Батч сохранен в БД");
    }
}
