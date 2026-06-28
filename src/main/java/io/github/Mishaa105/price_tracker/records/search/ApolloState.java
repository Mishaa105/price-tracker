package io.github.Mishaa105.price_tracker.records.search;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApolloState(Map<String, Product> products)
{
    private static final ObjectMapper mapper = new ObjectMapper();

    public ApolloState(Map<String, Product> products)
    {
        this.products = products != null ? products : new HashMap<>();
    }

    public Collection<Product> getProducts()
    {
        return products.values();
    }

    @JsonAnySetter
    public void deserializationFieldsWithDynamicName(String key, JsonNode value)
    {

        if (key.startsWith("Product:"))
        {
            products.put(key, mapper.treeToValue(value, Product.class));
        }
    }
}
