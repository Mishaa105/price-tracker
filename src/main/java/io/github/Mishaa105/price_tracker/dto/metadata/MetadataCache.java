package io.github.Mishaa105.price_tracker.dto.metadata;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;

public record MetadataCache(Map<String, Metadata> metadataMap)
{
    private static final ObjectMapper mapper = new ObjectMapper();

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

        if (key.startsWith("Product:"))
        {
            metadataMap.put(key, mapper.treeToValue(value, Metadata.class));
        }

    }
}
