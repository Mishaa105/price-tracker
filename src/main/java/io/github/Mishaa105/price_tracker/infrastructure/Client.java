package io.github.Mishaa105.price_tracker.infrastructure;

import io.github.Mishaa105.price_tracker.Config;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Client
{
    private String baseUrl;

    public Client(Config config)
    {
        this.baseUrl = config.playstation().baseUrl();
    }

    public Document htmlLoader()
    {
        Document htmlDoc;
        try
        {
            htmlDoc = Jsoup.connect(baseUrl).get();
            // В будущем сюда будет внедрена система поиска, которая будет дополнять baseUrl в зависимости от запроса пользователя
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        return htmlDoc;
    }

    public void testPrint()
    {
        System.out.println(htmlLoader());
    }

}
