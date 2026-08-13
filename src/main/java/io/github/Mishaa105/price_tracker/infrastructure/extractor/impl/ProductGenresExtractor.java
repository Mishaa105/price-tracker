package io.github.Mishaa105.price_tracker.infrastructure.extractor.impl;

import io.github.Mishaa105.price_tracker.dto.product.genre.ProductGenresResponse;
import io.github.Mishaa105.price_tracker.infrastructure.extractor.AbstractHtmlJsonExtractor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class ProductGenresExtractor extends AbstractHtmlJsonExtractor<ProductGenresResponse>
{
    private static final String SCRIPT = "script[id^=env:]:containsData(\"localizedGenres\")";

    public ProductGenresExtractor(ObjectMapper mapper)
    {
        super(mapper);
    }

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
