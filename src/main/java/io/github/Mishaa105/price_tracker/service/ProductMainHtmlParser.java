package io.github.Mishaa105.price_tracker.service;

import io.github.Mishaa105.price_tracker.dto.main.ProductMainResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductMainHtmlParser extends HtmlPayloadHtmlParser<ProductMainResponse>
{
    private static final String SCRIPT = "script[id^=env:]:containsData(\"originalPriceFormatted\")";

    @Override
    protected String getScript()
    {
        return SCRIPT;
    }

    @Override
    protected Class<ProductMainResponse> getTargetClass()
    {
        return ProductMainResponse.class;
    }
}