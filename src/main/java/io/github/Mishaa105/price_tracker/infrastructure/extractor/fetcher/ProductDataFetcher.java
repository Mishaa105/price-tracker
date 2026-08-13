package io.github.Mishaa105.price_tracker.infrastructure.extractor.fetcher;

import io.github.Mishaa105.price_tracker.dto.product.ProductResponse;
import io.github.Mishaa105.price_tracker.infrastructure.client.JsoupClient;
import io.github.Mishaa105.price_tracker.infrastructure.extractor.impl.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductDataFetcher
{
    private final JsoupClient jsoupClient;
    private final ProductOfferExtractor offerExtractor;
    private final ProductMediaExtractor mediaExtractor;
    private final ProductMetadataExtractor metadataExtractor;
    private final ProductLanguagesExtractor languagesExtractor;
    private final ProductGenresExtractor genresExtractor;

    private Document getHtmlDocument(String region, String id)
    {
        return jsoupClient.loadHtmlPage(region, JsoupClient.RequestType.PRODUCT, id);
    }

    public ProductResponse getProductResponse(String region, String id)
    {
        Document htmlDocument = getHtmlDocument(region, id);

        if (htmlDocument != null)
        {

            return new ProductResponse(
                    offerExtractor.getResponse(htmlDocument),
                    mediaExtractor.getResponse(htmlDocument),
                    metadataExtractor.getResponse(htmlDocument),
                    genresExtractor.getResponse(htmlDocument),
                    languagesExtractor.getResponse(htmlDocument));
        }

        log.warn("Html документ продукта с ID {} пуст", id);

        return null;
    }
}
