package io.github.Mishaa105.price_tracker;

import io.github.Mishaa105.price_tracker.controller.ConsoleController;
import io.github.Mishaa105.price_tracker.infrastructure.RestApiClient;
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
    public CommandLineRunner run(ConsoleController controller, RestApiClient restApiClient, DataBaseUpdateService dataBaseUpdateService)
    {
        return _ ->
        {
            String ps5 = restApiClient.getData("https://web.np.playstation.com/api/graphql/v1//op?operationName=categoryGridRetrieve&variables=%7B%22id%22%3A%22d0446d4b-dc9a-4f1e-86ec-651f099c9b29%22%2C%22pageArgs%22%3A%7B%22size%22%3A24%2C%22offset%22%3A24%7D%2C%22sortBy%22%3A%7B%22name%22%3A%22downloads30%22%2C%22isAscending%22%3Afalse%7D%2C%22filterBy%22%3A%5B%22targetPlatforms%3APS5%22%5D%2C%22facetOptions%22%3A%5B%5D%7D&extensions=%7B%22persistedQuery%22%3A%7B%22version%22%3A1%2C%22sha256Hash%22%3A%224e41660b6732f35c99fc5541926b7502a09557924e8c2cfebd1beb1a5c8c8f81%22%7D%7D");
            String dlc = restApiClient.getData("https://web.np.playstation.com/api/graphql/v1//op?operationName=categoryGridRetrieve&variables=%7B%22id%22%3A%2251c9aa7a-c0c7-4b68-90b4-328ad11bf42e%22%2C%22pageArgs%22%3A%7B%22size%22%3A24%2C%22offset%22%3A1%7D%2C%22sortBy%22%3Anull%2C%22filterBy%22%3A%5B%22productGenres%3AACTION%22%5D%2C%22facetOptions%22%3A%5B%5D%7D&extensions=%7B%22persistedQuery%22%3A%7B%22version%22%3A1%2C%22sha256Hash%22%3A%224e41660b6732f35c99fc5541926b7502a09557924e8c2cfebd1beb1a5c8c8f81%22%7D%7D");
            dataBaseUpdateService.bdSaveTest(ps5);
//            controller.start();
        };
        //Unavailable (gta 6 edition updater)
    }
}
