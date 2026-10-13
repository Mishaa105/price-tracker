package io.github.Mishaa105.price_tracker.infrastructure.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class FrankfurterClient
{
    private final RestClient restClient;

    public FrankfurterClient(RestClient.Builder builder)
    {
        this.restClient = builder.baseUrl("http://frankfurter:8080").build();
    }

    public String getExchangeRatesData(String date)
    {
        return restClient.get().uri("/v2/rates?date=" + date +"&base=RUB&quotes=USD,EUR,TRY,UAH,IDR").retrieve().body(String.class);
    }
}
