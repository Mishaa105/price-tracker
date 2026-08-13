package io.github.Mishaa105.price_tracker.dto.product.language;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import io.github.Mishaa105.price_tracker.util.DynamicNodeParser;
import tools.jackson.databind.JsonNode;

import java.util.LinkedHashMap;
import java.util.Map;

public record LanguageCache(Map<String, LanguageData> languageMap)
{
    public LanguageCache(Map<String, LanguageData> languageMap)
    {
        this.languageMap = languageMap != null ? languageMap : new LinkedHashMap<>();
    }

    public LanguageData languageData()
    {
        return languageMap.values().stream().findFirst().orElse(null);
    }

    @JsonAnySetter
    public void deserializationFieldsWithDynamicName(String key, JsonNode value)
    {
        String prefix = "Product:";
        DynamicNodeParser.parseAndPut(key, value, LanguageData.class, languageMap, prefix);
    }
}
