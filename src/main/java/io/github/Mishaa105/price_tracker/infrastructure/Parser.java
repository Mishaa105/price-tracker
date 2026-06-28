package io.github.Mishaa105.price_tracker.infrastructure;

import org.jsoup.nodes.Document;

public interface Parser<T>
{
    String extractJson(Document htmlDoc);
    T deserialization(String jsonString);
}
