package io.github.Mishaa105.price_tracker.service;

import io.github.Mishaa105.price_tracker.dto.language.LanguagesDataResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductLanguagesHtmlParser extends HtmlPayloadHtmlParser<LanguagesDataResponse>
{
    private static final String SCRIPT = "script[id^=env:]:containsData(\"spokenLanguagesByPlatform\")";

    @Override
    protected String getScript()
    {
        return SCRIPT;
    }

    @Override
    protected Class<LanguagesDataResponse> getTargetClass()
    {
        return LanguagesDataResponse.class;
    }
}
