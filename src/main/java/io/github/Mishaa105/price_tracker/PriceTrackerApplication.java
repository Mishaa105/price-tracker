package io.github.Mishaa105.price_tracker;

import io.github.Mishaa105.price_tracker.infrastructure.Client;
import io.github.Mishaa105.price_tracker.infrastructure.ProductPageParser;
import io.github.Mishaa105.price_tracker.infrastructure.SearchPageParser;
import io.github.Mishaa105.price_tracker.records.product.ExclusiveDiscountData;
import io.github.Mishaa105.price_tracker.records.product.PlayStationProductResponse;
import io.github.Mishaa105.price_tracker.records.product.SkuPriceDetail;
import io.github.Mishaa105.price_tracker.records.search.PlayStationSearchResponse;
import io.github.Mishaa105.price_tracker.records.search.Product;
import org.jsoup.nodes.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.List;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PriceTrackerApplication
{

    static void main(String[] args)
    {
        SpringApplication.run(PriceTrackerApplication.class, args);
    }

    public void test(PlayStationProductResponse response)
    {
        String name = response.cache().gameName().invariantName();
        String time = response.cache().basePrice().local().offerAvailability();
        String lowest = response.cache().basePrice().local().lowestRecentPrice();
        String percents = response.cache().basePrice().local().discountBadgeText();
        String basePrice = String.valueOf(response.cache().basePrice().local().telemetryMeta().skuDetail().skuPriceDetail().getFirst().originalPriceValue());
        String discountedPrice = String.valueOf(response.cache().basePrice().local().telemetryMeta().skuDetail().skuPriceDetail().getFirst().discountPriceValue());
        System.out.println(name + " " + basePrice + " " + discountedPrice);
        System.out.println(time + " " + lowest + " " + percents);
        Collection<ExclusiveDiscountData> excDiscounts = response.cache().exclusiveDiscount();

        for(ExclusiveDiscountData discount : excDiscounts)
        {
            List<SkuPriceDetail> skuPriceDetails = discount.local().telemetryMeta().skuDetail().skuPriceDetail();
            int priceWithDiscount = skuPriceDetails.getFirst().discountPriceValue();
            String priceCond = skuPriceDetails.getFirst().offerBranding();

            System.out.println("Стоимость товара составляет " + priceWithDiscount + " при наличии " + priceCond);
        }
    }

    public void testSearch(PlayStationSearchResponse response)
    {
        Collection<Product> products = response.props().apolloState().getProducts();
        int n = 1;
        for(Product product : products)
        {
            System.out.println("Игра " + n);
            n++;
            String id = product.id();
            System.out.println(id);

            for (int i = 0; i < product.platforms().size(); i++)
            {
                String platforms = product.platforms().get(i);
                System.out.println(platforms);
            }

            String image = product.media().getLast().url();

            System.out.println(image);
        }
    }

    @Bean
    public CommandLineRunner run(Client client, ProductPageParser parser2, SearchPageParser parser1)
    {
        return _ ->
        {
            String region = "/en-us/";
            String productId = "UP1004-PPSA03420_00-GTAVCROSSGENBUND";
            Document htmlDoc = client.htmlPageLoader(region, Client.RequestType.SEARCH, "grand theft");

            
            if(htmlDoc == null)
            {
                return;
            }
//
            String data = parser1.extractJson(htmlDoc);
//
            if(data != null)
            {
                testSearch(parser1.deserialization(data));
            }
        };
    }
}
