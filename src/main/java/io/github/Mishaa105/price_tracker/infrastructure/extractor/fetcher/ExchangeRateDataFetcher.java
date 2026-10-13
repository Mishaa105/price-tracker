package io.github.Mishaa105.price_tracker.infrastructure.extractor.fetcher;

import io.github.Mishaa105.price_tracker.dto.exchange_rate.Data;
import io.github.Mishaa105.price_tracker.infrastructure.client.FrankfurterClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeRateDataFetcher
{
    private final ObjectMapper mapper;
    private final FrankfurterClient frankfurterClient;

    public List<Data> getExchangeRates()
    {
        String rawJson = getRawExchangeRates();

        return mapper.readValue(rawJson, new TypeReference<>() {});
    }

    private String getRawExchangeRates()
    {
        LocalDate date = LocalDate.now();
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return  frankfurterClient.getExchangeRatesData(formattedDate);
    }
}
