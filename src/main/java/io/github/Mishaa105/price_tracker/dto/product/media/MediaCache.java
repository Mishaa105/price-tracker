package io.github.Mishaa105.price_tracker.dto.product.media;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import io.github.Mishaa105.price_tracker.util.DynamicNodeParser;
import tools.jackson.databind.JsonNode;

import java.util.LinkedHashMap;
import java.util.Map;

public record MediaCache(Map<String, MediaData> mediaDataMap)
{
    public MediaCache(Map<String, MediaData> mediaDataMap)
    {
        this.mediaDataMap = mediaDataMap != null ? mediaDataMap : new LinkedHashMap<>();
    }

    public MediaData mediaData()
    {
        return mediaDataMap.values().stream().findFirst().orElse(null);
    }

    @JsonAnySetter
    public void deserializationFieldsWithDynamicName(String key, JsonNode value)
    {
        String prefix = "Product:";
        DynamicNodeParser.parseAndPut(key, value, MediaData.class, mediaDataMap, prefix);
    }
}
