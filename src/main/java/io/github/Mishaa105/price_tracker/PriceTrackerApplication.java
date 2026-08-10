package io.github.Mishaa105.price_tracker;

import io.github.Mishaa105.price_tracker.controller.ConsoleController;
import io.github.Mishaa105.price_tracker.service.DataBaseUpdateService;
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
    public CommandLineRunner run(ConsoleController controller, DataBaseUpdateService dataBaseUpdateService)
    {
        return _ ->
        {
            dataBaseUpdateService.bdSaveTest();
//            controller.start();
        };
        //Unavailable (gta 6 edition updater)
        //Большой скрипт и универсальный cache
    }
}
