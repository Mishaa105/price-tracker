package io.github.Mishaa105.price_tracker.infrastructure;

import io.github.Mishaa105.price_tracker.records.product.PlayStationProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductPageParser extends HtmlPayloadParser<PlayStationProductResponse>
{
    String script = "script[id^=env:]:containsData(\"originalPriceFormatted\")";

    @Override
    protected String getScript()
    {
        return script;
    }

    @Override
    protected Class<PlayStationProductResponse> getTargetClass()
    {
        return PlayStationProductResponse.class;
    }
}