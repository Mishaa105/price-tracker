package io.github.Mishaa105.price_tracker.infrastructure.extractor;

import org.jsoup.nodes.Document;

public interface Extractor<T>
{
    String extractJson(Document htmlDocument);
    T deserialize(String jsonString);
    T getResponse(Document htmlDocument);
}
