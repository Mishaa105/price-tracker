package io.github.Mishaa105.price_tracker.service;

import io.github.Mishaa105.price_tracker.dto.product.PlayStationProductResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductPageHtmlParser extends HtmlPayloadHtmlParser<PlayStationProductResponse>
{
    private static final String SCRIPT = "script[id^=env:]:containsData(\"originalPriceFormatted\")";

    @Override
    protected String getScript()
    {
        return SCRIPT;
    }

    @Override
    protected Class<PlayStationProductResponse> getTargetClass()
    {
        return PlayStationProductResponse.class;
    }
}