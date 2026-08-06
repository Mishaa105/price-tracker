package io.github.Mishaa105.price_tracker.service;

import io.github.Mishaa105.price_tracker.dto.search.PlayStationSearchResponse;
import org.springframework.stereotype.Service;


@Service
public class SearchPageHtmlParser extends HtmlPayloadHtmlParser<PlayStationSearchResponse>
{
    private static final String SCRIPT = "script[id^=__NEXT_DATA__]";

    @Override
    protected String getScript()
    {
        return SCRIPT;
    }

    @Override
    protected Class<PlayStationSearchResponse> getTargetClass()
    {
        return PlayStationSearchResponse.class;
    }
}
