package io.github.Mishaa105.price_tracker.dto.product.genre;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import io.github.Mishaa105.price_tracker.util.DynamicNodeParser;
import tools.jackson.databind.JsonNode;

import java.util.LinkedHashMap;
import java.util.Map;

public record GenreCache(Map<String, GenreData> genreDataMap)
{
    public GenreCache(Map<String, GenreData> genreDataMap)
    {
        this.genreDataMap = genreDataMap != null ? genreDataMap : new LinkedHashMap<>();
    }

    public GenreData genreData()
    {
        return genreDataMap.values().stream().reduce((_, second) -> second).orElse(null);
    }

    @JsonAnySetter
    public void deserializationFieldsWithDynamicName(String key, JsonNode value)
    {
        String prefix = "Product:";
        DynamicNodeParser.parseAndPut(key, value, GenreData.class, genreDataMap, prefix);
    }
}
