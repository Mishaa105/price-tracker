package io.github.Mishaa105.price_tracker.infrastructure.batch;

import io.github.Mishaa105.price_tracker.infrastructure.db.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public void saveBatchToDb(ProductBatch batch)
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
}
