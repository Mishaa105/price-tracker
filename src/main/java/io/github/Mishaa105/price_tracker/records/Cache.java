package io.github.Mishaa105.price_tracker.records;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Cache(Map<String, ExclusiveDiscountData> exclusiveDiscountData, Map<String, GameName> nameData,
                    Map<String, BasePriceData> basePriceData)
{
    public Cache(Map<String, ExclusiveDiscountData> exclusiveDiscountData, Map<String, GameName> nameData, Map<String, BasePriceData> basePriceData)
    {
        this.exclusiveDiscountData = exclusiveDiscountData != null ? exclusiveDiscountData : new HashMap<>();
        this.nameData = nameData != null ? nameData : new HashMap<>();
        this.basePriceData = basePriceData != null ? basePriceData : new HashMap<>();
    }

    public GameName gameName()
    {
        return nameData.values().stream().findFirst().orElse(new GameName("Unknown", "Unknown"));
    }

    public BasePriceData basePrice()
    {
        return basePriceData.values().stream().findFirst().orElse(null);
    }

    public ExclusiveDiscountData exclusiveDiscount()
    {
        return exclusiveDiscountData.values().stream().findFirst().orElse(null);
    }

    @JsonAnySetter
    public void deserializationFieldsWithDynamicName(String key, JsonNode value)
    {
        ObjectMapper mapper = new ObjectMapper();

        if (key.startsWith("Product:"))
        {
            nameData.put(key, mapper.treeToValue(value, GameName.class));
        }
        else if (key.startsWith("GameCTA:ADD_TO_CART:"))
        {
            basePriceData.put(key, mapper.treeToValue(value, BasePriceData.class));
        }
        else if (key.startsWith("GameCTA:UPSELL_"))
        {
            exclusiveDiscountData.put(key, mapper.treeToValue(value, ExclusiveDiscountData.class));
        }
    }
}
