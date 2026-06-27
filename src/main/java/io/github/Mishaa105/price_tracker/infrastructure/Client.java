package io.github.Mishaa105.price_tracker.infrastructure;

import io.github.Mishaa105.price_tracker.Config;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class Client
{
    private final String baseUrl;

    public Client(Config config)
    {
        this.baseUrl = config.playstation().baseUrl();
    }

    public Document htmlLoader()
    {
        String region = "/en-us";
        String productTag = "/product/";
        String gameId = "UP0001-PPSA01518_00-STANDARDEDITION0";
        String url = baseUrl + region + productTag + gameId;
        // В будущем сюда будет внедрена система поиска, которая будет дополнять baseUrl в зависимости от запроса пользователя

        log.debug("Отправка GET-запроса по адресу {}", url);

        try
        {
            org.jsoup.Connection.Response response = Jsoup.connect(url).execute();

            String finalUrl = response.url().toString();
            if (!finalUrl.equalsIgnoreCase(url))
            {
                log.error("Игра с ID {} не найдена (переадресация на главную страницу)", gameId);
                return null;
            }

            Document htmlDoc = response.parse();
            log.info("Html файл успешно загружен");
            return htmlDoc;
        }
        catch (org.jsoup.HttpStatusException e)
        {
            int statusCode = e.getStatusCode();
            if(statusCode >= 400 && statusCode <= 499)
            {
                if(statusCode == 404)
                {
                    log.error("Игра с ID {} не найдена (крайний случай)", gameId);
                }
                else
                {
                    log.error("Ошибка {} на стороне клиента по адресу {}", statusCode, url);
                }
            }
            else if(statusCode >= 500 && statusCode <= 599)
            {
                log.warn("Ошибка {} на стороне PlayStation. Нужно подождать", statusCode);
            }
            else
            {
                log.error("Неожиданная ошибка {}", statusCode);
            }
        }
        catch (java.net.ConnectException e)
        {
            log.error("Нет подключения к интернету");
        }
        catch (IOException e)
        {
            log.error("IOException при загрузке html файла по адресу {}", url, e);
        }
    
        return null;
    }
}
