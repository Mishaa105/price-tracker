package io.github.Mishaa105.price_tracker.service;

import io.github.Mishaa105.price_tracker.dto.description.ProductMetadataResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductMetadataHtmlParser extends HtmlPayloadHtmlParser<ProductMetadataResponse>
{
    private static final String SCRIPT = "script[id^=env:]:containsData(\"showRatingInfo\")";

    @Override
    protected String getScript()
    {
        return SCRIPT;
    }

    @Override
    protected Class<ProductMetadataResponse> getTargetClass()
    {
        return ProductMetadataResponse.class;
    }
}
