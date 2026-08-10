package io.github.Mishaa105.price_tracker.service;

import io.github.Mishaa105.price_tracker.dto.language.LanguageDataResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductLanguagesHtmlParser extends HtmlPayloadHtmlParser<LanguageDataResponse>
{
    private static final String SCRIPT = "script[id^=env:]:containsData(\"spokenLanguagesByPlatform\")";

    @Override
    protected String getScript()
    {
        return SCRIPT;
    }

    @Override
    protected Class<LanguageDataResponse> getTargetClass()
    {
        return LanguageDataResponse.class;
    }
}
