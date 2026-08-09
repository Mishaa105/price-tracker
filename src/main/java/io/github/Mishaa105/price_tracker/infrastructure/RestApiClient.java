package io.github.Mishaa105.price_tracker.infrastructure;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;

@Component
public class RestApiClient
{
    private final RestClient restClient;

    public RestApiClient(RestClient.Builder builder)
    {
        this.restClient = builder.build();
    }

    public String getData(String url, String locale)
    {
        return restClient.get()
                .uri(URI.create(url))
                .header("x-psn-store-locale-override", locale)
                .retrieve()
                .body(String.class);
    }
}
