package io.github.Mishaa105.price_tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig
{
    @Bean
    public RestClient.Builder restClientBuilder()
    {
        return RestClient.builder()
                .defaultHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/151.0.0.0 Safari/537.36")
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("apollographql-client-version", "0.112.1")
                .defaultHeader("x-psn-store-locale-override", "en-US")
                .defaultHeader("Sec-CH-UA", "\"Google Chrome\";v=\"151\", \"Chromium\";v=\"151\", \"Not?A_Brand\";v=\"24\"")
                .defaultHeader("Sec-CH-UA-Mobile", "?0")
                .defaultHeader("Sec-CH-UA-Platform", "\"Windows\"")
                .defaultHeader("Sec-Fetch-Dest", "empty")
                .defaultHeader("Sec-Fetch-Mode", "cors")
                .defaultHeader("Sec-Fetch-Site", "same-origin")
                .defaultHeader("Accept-Encoding", "gzip, deflate, br");
    }
}