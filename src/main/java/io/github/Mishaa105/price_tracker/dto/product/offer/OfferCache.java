package io.github.Mishaa105.price_tracker.dto.product.offer;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import io.github.Mishaa105.price_tracker.util.DynamicNodeParser;
import tools.jackson.databind.JsonNode;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public record OfferCache(Map<String, GameName> nameDataMap, Map<String, PriceData> priceDataMap)
{
    public OfferCache(Map<String, GameName> nameDataMap, Map<String, PriceData> priceDataMap)
    {
        this.nameDataMap = nameDataMap != null ? nameDataMap : new LinkedHashMap<>();
        this.priceDataMap = priceDataMap != null ? priceDataMap : new LinkedHashMap<>();
    }

    public GameName gameName()
    {
        return nameDataMap.values().stream().findFirst().orElse(new GameName("Unknown", "Unknown"));
    }

    public Collection<PriceData> basePrice()
    {
        return priceDataMap.values();
    }

    @JsonAnySetter
    public void deserializationFieldsWithDynamicName(String key, JsonNode value)
    {
        String namePrefix = "Product:";
        String pricePrefix = "GameCTA:";

        DynamicNodeParser.parseAndPut(key, value, GameName.class, nameDataMap, namePrefix);
        DynamicNodeParser.parseAndPut(key, value, PriceData.class, priceDataMap, pricePrefix);
    }
}
