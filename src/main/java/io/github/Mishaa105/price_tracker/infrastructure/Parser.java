package io.github.Mishaa105.price_tracker.infrastructure;

import io.github.Mishaa105.price_tracker.records.PlayStationResponse;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class Parser
{
    private final ObjectMapper mapper;

    public Parser(ObjectMapper mapper)
    {
        this.mapper = mapper;
    }

    public String extractJson(Document htmlDoc)
    {
        Element data = htmlDoc.selectFirst("script[id^=env:]:containsData(\"originalPriceFormatted\")");

        if(data != null)
        {
            return data.html().trim();
        }
        return null;
    }

    public PlayStationResponse deserialization(String jsonString)
    {
        return mapper.readValue(jsonString, PlayStationResponse.class);
    }

    public void test(PlayStationResponse response)
    {
        String name = response.cache().gameName().invariantName();
        String basePrice = String.valueOf(response.cache().basePrice().local().telemetryMeta().skuDetail().skuPriceDetail().getFirst().originalPriceValue());
        String discountedPrice = String.valueOf(response.cache().basePrice().local().telemetryMeta().skuDetail().skuPriceDetail().getFirst().discountPriceValue());
        System.out.println(name + " " + basePrice + " " + discountedPrice);
    }
}
