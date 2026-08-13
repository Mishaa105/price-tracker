package io.github.Mishaa105.price_tracker.infrastructure.extractor.impl;

import io.github.Mishaa105.price_tracker.dto.product.language.ProductLanguagesResponse;
import io.github.Mishaa105.price_tracker.infrastructure.extractor.AbstractHtmlJsonExtractor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class ProductLanguagesExtractor extends AbstractHtmlJsonExtractor<ProductLanguagesResponse>
{
    private static final String SCRIPT = "script[id^=env:]:containsData(\"spokenLanguagesByPlatform\")";

    public ProductLanguagesExtractor(ObjectMapper mapper)
    {
        super(mapper);
    }

    @Override
    protected String getScript()
    {
        return SCRIPT;
    }

    @Override
    protected Class<ProductLanguagesResponse> getTargetClass()
    {
        return ProductLanguagesResponse.class;
    }
}
