package io.github.Mishaa105.price_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PriceTrackerApplication
{

    static void main(String[] args)
    {
        SpringApplication.run(PriceTrackerApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner run(PlayStationClient client)
//    {
//        return _ ->
//        {
//
//        };
//
//        //Большой скрипт и универсальный cache
//    }
}
