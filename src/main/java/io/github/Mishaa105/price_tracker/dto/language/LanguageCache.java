package io.github.Mishaa105.price_tracker.dto.language;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;

public record LanguageCache(Map<String, LanguageData> languageMap)
{
    private static final ObjectMapper mapper = new ObjectMapper();

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

        if (key.startsWith("Product:"))
        {
            languageMap.put(key, mapper.treeToValue(value, LanguageData.class));
        }

    }
}
