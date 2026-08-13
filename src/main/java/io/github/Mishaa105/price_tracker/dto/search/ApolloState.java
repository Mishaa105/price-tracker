package io.github.Mishaa105.price_tracker.dto.search;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import io.github.Mishaa105.price_tracker.util.DynamicNodeParser;
import tools.jackson.databind.JsonNode;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public record ApolloState(Map<String, Product> productMap)
{
    public ApolloState(Map<String, Product> productMap)
    {
        this.productMap = productMap != null ? productMap : new LinkedHashMap<>();
    }

    public Collection<Product> getProducts()
    {
        return productMap.values();
    }

    @JsonAnySetter
    public void deserializationFieldsWithDynamicName(String key, JsonNode value)
    {
        String prefix = "Product:";
        DynamicNodeParser.parseAndPut(key, value, Product.class, productMap, prefix);
    }
}
