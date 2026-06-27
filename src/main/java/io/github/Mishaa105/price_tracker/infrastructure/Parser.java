package io.github.Mishaa105.price_tracker.infrastructure;

import io.github.Mishaa105.price_tracker.records.ExclusiveDiscountData;
import io.github.Mishaa105.price_tracker.records.PlayStationResponse;
import io.github.Mishaa105.price_tracker.records.SkuPriceDetail;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class Parser
{
    private final ObjectMapper mapper;

    public Parser(ObjectMapper mapper)
    {
        this.mapper = mapper;
    }

    public String extractJson(Document htmlDoc)
    {
        if(htmlDoc == null)
        {
            log.error("Переданный html равен null");
            return null;
        }

        log.info("Загрузили html в extractor");

        Element data = htmlDoc.selectFirst("script[id^=env:]:containsData(\"originalPriceFormatted\")");

        log.info("Нашли json с ценами");

        if(data != null)
        {
            log.info("Extractor завершил работу");
            return data.html().trim();
        }
        log.warn("Не найден json с ценами");
        return null;
    }

    public PlayStationResponse deserialization(String jsonString)
    {
        log.info("Началась десериализация");

        if(jsonString == null)
        {
            log.error("На вход в десериализатор пришла строка равная null");
            return null;
        }

        try
        {
            PlayStationResponse response = mapper.readValue(jsonString, PlayStationResponse.class);
            log.info("Десериализация завершена успешно");
            return response;
        }
        catch (tools.jackson.core.JacksonException e)
        {
            log.error("Ошибка при десериализации JSON", e);
            return null;
        }
    }

    public void test(PlayStationResponse response)
    {
        String name = response.cache().gameName().invariantName();
        String basePrice = String.valueOf(response.cache().basePrice().local().telemetryMeta().skuDetail().skuPriceDetail().getFirst().originalPriceValue());
        String discountedPrice = String.valueOf(response.cache().basePrice().local().telemetryMeta().skuDetail().skuPriceDetail().getFirst().discountPriceValue());
        System.out.println(name + " " + basePrice + " " + discountedPrice);
        Collection<ExclusiveDiscountData> excDiscounts = response.cache().exclusiveDiscount();

        for(ExclusiveDiscountData discount : excDiscounts)
        {
            List<SkuPriceDetail> skuPriceDetails = discount.local().telemetryMeta().skuDetail().skuPriceDetail();
            int priceWithDiscount = skuPriceDetails.getFirst().discountPriceValue();
            String priceCond = skuPriceDetails.getFirst().offerBranding();

            System.out.println("Стоимость товара составляет " + priceWithDiscount + " при наличии " + priceCond);
        }
    }
}
