package io.github.Mishaa105.price_tracker.dto.media;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.util.LinkedHashMap;
import java.util.Map;

public record Cache(@JsonDeserialize(as = LinkedHashMap.class) Map<String, MediaData> mediaDataMap)
{
    private static final ObjectMapper mapper = new ObjectMapper();

    public Cache(Map<String, MediaData> mediaDataMap)
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
        if (key.startsWith("Product:"))
        {
            mediaDataMap.put(key, mapper.treeToValue(value, MediaData.class));
        }
    }
}
