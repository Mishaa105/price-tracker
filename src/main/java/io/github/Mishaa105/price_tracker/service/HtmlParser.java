package io.github.Mishaa105.price_tracker.service;

import org.jsoup.nodes.Document;

public interface HtmlParser<T>
{
    String extractJson(Document htmlDoc);
    T deserialize(String jsonString);
}
