package io.github.Mishaa105.price_tracker.dto.genre;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;

public record GenreCache(Map<String, GenreData> genreDataMap)
{
    private static final ObjectMapper mapper = new ObjectMapper();

    public GenreCache(Map<String, GenreData> genreDataMap)
    {
        this.genreDataMap = genreDataMap != null ? genreDataMap : new LinkedHashMap<>();
    }

    public GenreData genreData()
    {
        return genreDataMap.values().stream().findFirst().orElse(null);
    }

    @JsonAnySetter
    public void deserializationFieldsWithDynamicName(String key, JsonNode value)
    {
        if (key.startsWith("Product:"))
        {
            genreDataMap.put(key, mapper.treeToValue(value, GenreData.class));
        }
    }
}
