package io.github.Mishaa105.price_tracker.infrastructure;

import io.github.Mishaa105.price_tracker.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class JsoupClient
{
    private final String baseUrl;

    public JsoupClient(Config config)
    {
        this.baseUrl = config.playstation().baseUrl();
    }

    public enum RequestType
    {
        SEARCH, PRODUCT
    }

    public Document loadHtmlPage(String region, RequestType requestType, String request)
    {
        String clearRequest = request.replaceAll(" ", "%20");

        String tag = switch (requestType)
        {
            case SEARCH ->
            {
                log.info("Режим вывода страницы поиска");
                yield "search/";
            }
            case PRODUCT ->
            {
                log.info("Режим вывода страницы продукта");
                yield "product/";
            }
        };

        String url = baseUrl + region + tag + clearRequest;

        log.debug("Отправка GET-запроса по адресу {}", url);

        try
        {
            Response response = Jsoup.connect(url).execute();

            String finalUrl = response.url().toString();
            if (!finalUrl.equalsIgnoreCase(url))
            {
                log.error("Игра с ID {} не найдена (переадресация на главную страницу)", request);
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
                    log.error("Игра с ID {} не найдена (крайний случай)", request);
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
