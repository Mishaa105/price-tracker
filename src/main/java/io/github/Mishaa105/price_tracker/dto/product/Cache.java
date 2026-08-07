package io.github.Mishaa105.price_tracker.dto.product;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.util.*;

public record Cache(@JsonDeserialize(as = LinkedHashMap.class) Map<String, ExclusiveDiscountData> exclusiveDiscountData,
                    @JsonDeserialize(as = LinkedHashMap.class) Map<String, GameName> nameData,
                    @JsonDeserialize(as = LinkedHashMap.class) Map<String, BasePriceData> basePriceData,
                    @JsonDeserialize(as = LinkedHashMap.class) Map<String, PreorderPrice> preorderPriceData,
                    @JsonDeserialize(as = LinkedHashMap.class) Map<String, FreePrice> freeData,
                    @JsonDeserialize(as = LinkedHashMap.class) Map<String, SubscriptionPrice> subscriptionPriceData)
{
    private static final ObjectMapper mapper = new ObjectMapper();

    public Cache(Map<String, ExclusiveDiscountData> exclusiveDiscountData, Map<String, GameName> nameData, Map<String, BasePriceData> basePriceData, Map<String, PreorderPrice> preorderPriceData, Map<String, FreePrice> freeData, Map<String, SubscriptionPrice> subscriptionPriceData)
    {
        this.exclusiveDiscountData = exclusiveDiscountData != null ? exclusiveDiscountData : new LinkedHashMap<>();
        this.nameData = nameData != null ? nameData : new LinkedHashMap<>();
        this.basePriceData = basePriceData != null ? basePriceData : new LinkedHashMap<>();
        this.preorderPriceData = preorderPriceData != null ? preorderPriceData : new LinkedHashMap<>();
        this.freeData = freeData != null ? freeData : new LinkedHashMap<>();
        this.subscriptionPriceData = subscriptionPriceData != null ? subscriptionPriceData : new LinkedHashMap<>();
    }

    public GameName gameName()
    {
        return nameData.values().stream().findFirst().orElse(new GameName("Unknown", "Unknown"));
    }

    public BasePriceData basePrice()
    {
        return basePriceData.values().stream().findFirst().orElse(null);
    }

    public PreorderPrice preorderPrice()
    {
        return preorderPriceData.values().stream().findFirst().orElse(null);
    }

    public FreePrice freePrice()
    {
        return freeData.values().stream().findFirst().orElse(null);
    }

    public Collection<ExclusiveDiscountData> exclusiveDiscount()
    {
        return exclusiveDiscountData.values();
    }

    public SubscriptionPrice subscriptionPrice()
    {
        return subscriptionPriceData.values().stream().findFirst().orElse(null);
    }

    @JsonAnySetter
    public void deserializationFieldsWithDynamicName(String key, JsonNode value)
    {

        if (key.startsWith("Product:"))
        {
            nameData.put(key, mapper.treeToValue(value, GameName.class));
        } else if (key.startsWith("GameCTA:ADD_TO_CART:"))
        {
            basePriceData.put(key, mapper.treeToValue(value, BasePriceData.class));
        } else if (key.startsWith("GameCTA:UPSELL_"))
        {
            exclusiveDiscountData.put(key, mapper.treeToValue(value, ExclusiveDiscountData.class));
        } else if (key.startsWith("GameCTA:PREORDER:"))
        {
            preorderPriceData.put(key, mapper.treeToValue(value, PreorderPrice.class));
        } else if (key.startsWith("GameCTA:DOWNLOAD:"))
        {
            freeData.put(key, mapper.treeToValue(value, FreePrice.class));
        } else if (key.startsWith("GameCTA:BUY_NOW"))
        {
            subscriptionPriceData.put(key, mapper.treeToValue(value, SubscriptionPrice.class));
        }
    }
}
