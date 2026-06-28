package io.github.Mishaa105.price_tracker.infrastructure;

import io.github.Mishaa105.price_tracker.records.search.PlayStationSearchResponse;
import org.springframework.stereotype.Component;


@Component
public class SearchPageParser extends HtmlPayloadParser<PlayStationSearchResponse>
{
    String script = "script[id^=__NEXT_DATA__]";

    @Override
    protected String getScript()
    {
        return script;
    }

    @Override
    protected Class<PlayStationSearchResponse> getTargetClass()
    {
        return PlayStationSearchResponse.class;
    }
}
