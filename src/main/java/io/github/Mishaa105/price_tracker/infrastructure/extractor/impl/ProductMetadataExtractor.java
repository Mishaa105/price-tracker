package io.github.Mishaa105.price_tracker.infrastructure.extractor.impl;

import io.github.Mishaa105.price_tracker.dto.product.metadata.ProductMetadataResponse;
import io.github.Mishaa105.price_tracker.infrastructure.extractor.AbstractHtmlJsonExtractor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class ProductMetadataExtractor extends AbstractHtmlJsonExtractor<ProductMetadataResponse>
{
    private static final String SCRIPT = "script[id^=env:]:containsData(\"showRatingInfo\")";

    public ProductMetadataExtractor(ObjectMapper mapper)
    {
        super(mapper);
    }

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
