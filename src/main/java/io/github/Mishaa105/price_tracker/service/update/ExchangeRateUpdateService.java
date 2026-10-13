package io.github.Mishaa105.price_tracker.service.update;

import io.github.Mishaa105.price_tracker.dto.exchange_rate.Data;
import io.github.Mishaa105.price_tracker.infrastructure.extractor.fetcher.ExchangeRateDataFetcher;
import io.github.Mishaa105.price_tracker.infrastructure.saver.frankfurter.ExchangeRateSaver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExchangeRateUpdateService
{
    private final ExchangeRateDataFetcher exchangeRateDataFetcher;
    private final ExchangeRateSaver exchangeRateSaver;

    public void startExchangeRateUpdate()
    {
        List<Data> dataList = exchangeRateDataFetcher.getExchangeRates();
        exchangeRateSaver.saveExchangeRatesToDb(dataList);
        log.info("Обновление курсов валют завершено");
    }

}
