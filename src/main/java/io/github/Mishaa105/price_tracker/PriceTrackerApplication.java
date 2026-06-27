package io.github.Mishaa105.price_tracker;

import io.github.Mishaa105.price_tracker.infrastructure.Client;
import io.github.Mishaa105.price_tracker.infrastructure.Parser;
import org.jsoup.nodes.Document;
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
            Document htmlDoc = client.htmlLoader();
            
            if(htmlDoc == null)
            {
                return;
            }

            String data = parser.extractJson(htmlDoc);

            if(data != null)
            {
                parser.test(parser.deserialization(data));
            }
        };
    }
}
