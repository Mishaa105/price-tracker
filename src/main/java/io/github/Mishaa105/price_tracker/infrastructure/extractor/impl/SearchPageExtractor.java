package io.github.Mishaa105.price_tracker.infrastructure.extractor.impl;

import io.github.Mishaa105.price_tracker.dto.search.PlayStationSearchResponse;
import io.github.Mishaa105.price_tracker.infrastructure.extractor.AbstractHtmlJsonExtractor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;


@Component
public class SearchPageExtractor extends AbstractHtmlJsonExtractor<PlayStationSearchResponse>
{
    private static final String SCRIPT = "script[id^=__NEXT_DATA__]";

    public SearchPageExtractor(ObjectMapper mapper)
    {
        super(mapper);
    }

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
