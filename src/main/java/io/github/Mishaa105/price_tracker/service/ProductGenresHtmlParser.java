package io.github.Mishaa105.price_tracker.service;

import io.github.Mishaa105.price_tracker.dto.genre.ProductGenresResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductGenresHtmlParser extends HtmlPayloadHtmlParser<ProductGenresResponse>
{
    private static final String SCRIPT = "script[id^=env:]:containsData(\"localizedGenres\")";

    @Override
    protected String getScript()
    {
        return SCRIPT;
    }

    @Override
    protected Class<ProductGenresResponse> getTargetClass()
    {
        return ProductGenresResponse.class;
    }
}
