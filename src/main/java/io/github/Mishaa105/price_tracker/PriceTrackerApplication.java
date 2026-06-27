package io.github.Mishaa105.price_tracker;

import io.github.Mishaa105.price_tracker.infrastructure.Client;
import io.github.Mishaa105.price_tracker.infrastructure.Parser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PriceTrackerApplication
{

    static void main(String[] args)
    {
        SpringApplication.run(PriceTrackerApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(Client client, Parser parser)
    {
        return _ ->
        {
            String data = parser.extractJson(client.htmlLoader());
            parser.test(parser.deserialization(data));
        };
    }

}
