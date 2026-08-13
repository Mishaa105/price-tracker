package io.github.Mishaa105.price_tracker.util;

import lombok.experimental.UtilityClass;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@UtilityClass
public class DynamicNodeParser
{
    private static final ObjectMapper mapper = new ObjectMapper();

    public <T> void parseAndPut(String key, JsonNode value, Class<T> targetClass, Map<String, T> targetMap , String prefix)
    {
        if (key.startsWith(prefix))
        {
            targetMap.put(key, mapper.treeToValue(value, targetClass));
        }
    }
}
