package io.github.Mishaa105.price_tracker.infrastructure.extractor.impl;

import io.github.Mishaa105.price_tracker.dto.product.offer.ProductOfferResponse;
import io.github.Mishaa105.price_tracker.infrastructure.extractor.AbstractHtmlJsonExtractor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class ProductOfferExtractor extends AbstractHtmlJsonExtractor<ProductOfferResponse>
{
    private static final String SCRIPT = "script[id^=env:]:containsData(\"originalPriceFormatted\")";

    public ProductOfferExtractor(ObjectMapper mapper)
    {
        super(mapper);
    }

    @Override
    protected String getScript()
    {
        return SCRIPT;
    }

    @Override
    protected Class<ProductOfferResponse> getTargetClass()
    {
        return ProductOfferResponse.class;
    }
}