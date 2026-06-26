package io.github.Mishaa105.price_tracker.infrastructure;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class Parser
{
    public String extractJson(Document htmlDoc)
    {
        Element data = htmlDoc.selectFirst("script[id^=env:]:containsData(\"originalPriceFormatted\")");

        if(data != null)
        {
            return data.html().trim();
        }
        return "Не нашли";
    }

    public void extractTest(Client client)
    {
        System.out.println(extractJson(client.htmlLoader()));
    }
}
