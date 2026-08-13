package io.github.Mishaa105.price_tracker.infrastructure.extractor.impl;

import io.github.Mishaa105.price_tracker.dto.product.media.ProductMediaResponse;
import io.github.Mishaa105.price_tracker.infrastructure.extractor.AbstractHtmlJsonExtractor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class ProductMediaExtractor extends AbstractHtmlJsonExtractor<ProductMediaResponse>
{
    private static final String SCRIPT = "script[id^=env:]:containsData(\"MASTER\")";

    public ProductMediaExtractor(ObjectMapper mapper)
    {
        super(mapper);
    }

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
