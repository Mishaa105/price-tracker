package io.github.Mishaa105.price_tracker.dto.product.metadata;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import io.github.Mishaa105.price_tracker.util.DynamicNodeParser;
import tools.jackson.databind.JsonNode;

import java.util.LinkedHashMap;
import java.util.Map;

public record MetadataCache(Map<String, Metadata> metadataMap)
{
    public MetadataCache(Map<String, Metadata> metadataMap)
    {
        this.metadataMap = metadataMap != null ? metadataMap : new LinkedHashMap<>();
    }

    public Metadata metadata()
    {
        return metadataMap.values().stream().findFirst().orElse(null);
    }

    @JsonAnySetter
    public void deserializationFieldsWithDynamicName(String key, JsonNode value)
    {
        String prefix = "Product:";
        DynamicNodeParser.parseAndPut(key, value, Metadata.class, metadataMap, prefix);
    }
}
