package io.github.Mishaa105.price_tracker.infrastructure.extractor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractHtmlJsonExtractor<T> implements Extractor<T>
{

    protected final ObjectMapper mapper;

    protected abstract String getScript();
    protected abstract Class<T> getTargetClass();

    @Override
    public String extractJson(Document htmlDoc)
    {
        if(htmlDoc == null)
        {
            log.error("Переданный html равен null");
            return null;
        }

        log.info("Загрузили html в extractor");

        Element data = htmlDoc.selectFirst(getScript());

        if(data != null)
        {
            log.info("Нашли нужный json. Extractor завершил работу");
            return data.html().trim();
        }
        log.warn("Не найден нужный json");
        return null;
    }

    @Override
    public T deserialize(String jsonString)
    {
        log.info("Началась десериализация");

        if(jsonString == null)
        {
            log.error("На вход в десериализатор пришла строка равная null");
            return null;
        }

        try
        {
            T response = mapper.readValue(jsonString, getTargetClass());
            log.info("Десериализация завершена успешно");
            return response;
        }
        catch (tools.jackson.core.JacksonException e)
        {
            log.error("Ошибка при десериализации JSON", e);
            return null;
        }
    }

    @Override
    public T getResponse(Document htmlDocument)
    {
        String jsonData = extractJson(htmlDocument);

        if(jsonData != null)
        {
            return deserialize(jsonData);
        }

        log.warn("Ошибка {} при получении данных ", getTargetClass());

        return null;
    }
}
