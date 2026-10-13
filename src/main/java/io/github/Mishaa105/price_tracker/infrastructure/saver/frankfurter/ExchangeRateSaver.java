package io.github.Mishaa105.price_tracker.infrastructure.saver.frankfurter;

import io.github.Mishaa105.price_tracker.dto.exchange_rate.Data;
import io.github.Mishaa105.price_tracker.infrastructure.db.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExchangeRateSaver
{
    private final CurrencyRepository currencyRepository;

    public void saveExchangeRatesToDb(List<Data> dataList)
    {
        for (Data data : dataList)
        {
            String currencyName = data.quote();
            Double exchangeRate = data.rate();

            int updatedRows = currencyRepository.updateExchangeRatesByCurrency(currencyName, exchangeRate);

            if(updatedRows > 0)
            {
                log.debug("{} обновлен", currencyName);
            }
            else
            {
                currencyRepository.createNewCurrencyWithExchangeRate(currencyName, exchangeRate);
                log.debug("{} создан", currencyName);
            }
        }
    }
}
