package io.github.Mishaa105.price_tracker.service;

import io.github.Mishaa105.price_tracker.dto.media.ProductMediaResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductMediaHtmlParser extends HtmlPayloadHtmlParser<ProductMediaResponse>
{
    private static final String SCRIPT = "script[id^=env:]:containsData(\"MASTER\")";

    @Override
    protected String getScript()
    {
        return SCRIPT;
    }

    @Override
    protected Class<ProductMediaResponse> getTargetClass()
    {
        return ProductMediaResponse.class;
    }
}
