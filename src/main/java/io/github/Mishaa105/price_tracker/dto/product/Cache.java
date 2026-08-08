package io.github.Mishaa105.price_tracker.dto.product;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.util.*;

public record Cache(@JsonDeserialize(as = LinkedHashMap.class) Map<String, GameName> nameData,
                    @JsonDeserialize(as = LinkedHashMap.class) Map<String, PriceData> priceData)
{
    private static final ObjectMapper mapper = new ObjectMapper();

    public Cache(Map<String, GameName> nameData, Map<String, PriceData> priceData)
    {
        this.nameData = nameData != null ? nameData : new LinkedHashMap<>();
        this.priceData = priceData != null ? priceData : new LinkedHashMap<>();
    }

    public GameName gameName()
    {
        return nameData.values().stream().findFirst().orElse(new GameName("Unknown", "Unknown"));
    }

    public Collection<PriceData> basePrice()
    {
        return priceData.values();
    }

    @JsonAnySetter
    public void deserializationFieldsWithDynamicName(String key, JsonNode value)
    {

        if (key.startsWith("Product:"))
        {
            nameData.put(key, mapper.treeToValue(value, GameName.class));
        }
        else if (key.startsWith("GameCTA:"))
        {
            priceData.put(key, mapper.treeToValue(value, PriceData.class));
        }
    }
}
