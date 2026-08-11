package io.github.Mishaa105.price_tracker.dto.main;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.*;

public record ProductCache(Map<String, GameName> nameDataMap, Map<String, PriceData> priceDataMap)
{
    private static final ObjectMapper mapper = new ObjectMapper();

    public ProductCache(Map<String, GameName> nameDataMap, Map<String, PriceData> priceDataMap)
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

        if (key.startsWith("Product:"))
        {
            nameDataMap.put(key, mapper.treeToValue(value, GameName.class));
        }
        else if (key.startsWith("GameCTA:"))
        {
            priceDataMap.put(key, mapper.treeToValue(value, PriceData.class));
        }
    }
}
