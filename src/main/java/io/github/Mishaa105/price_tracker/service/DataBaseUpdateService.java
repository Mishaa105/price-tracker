package io.github.Mishaa105.price_tracker.service;

import io.github.Mishaa105.price_tracker.dto.product.ProductResponse;
import io.github.Mishaa105.price_tracker.enums.graphql.GenreEnum;
import io.github.Mishaa105.price_tracker.enums.graphql.PlatformEnum;
import io.github.Mishaa105.price_tracker.enums.graphql.ProductTypeEnum;
import io.github.Mishaa105.price_tracker.enums.graphql.SortByEnum;
import io.github.Mishaa105.price_tracker.enums.regions.Regions;
import io.github.Mishaa105.price_tracker.infrastructure.batch.BatchBuilder;
import io.github.Mishaa105.price_tracker.infrastructure.batch.BatchSaver;
import io.github.Mishaa105.price_tracker.infrastructure.batch.ProductBatch;
import io.github.Mishaa105.price_tracker.infrastructure.client.GraphQlUrlBuilder;
import io.github.Mishaa105.price_tracker.infrastructure.client.RestApiClient;
import io.github.Mishaa105.price_tracker.infrastructure.extractor.fetcher.CatalogDataFetcher;
import io.github.Mishaa105.price_tracker.infrastructure.extractor.fetcher.ProductDataFetcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataBaseUpdateService
{
    private final CatalogDataFetcher catalogDataFetcher;
    private final RestApiClient restApiClient;
    private final ProductDataFetcher productDataFetcher;
    private final GraphQlUrlBuilder graphQlBuilder;
    private final BatchBuilder batchBuilder;
    private final BatchSaver batchSaver;

    public void bdSaveTest()
    {
        for (int i = 0; i < 50; i++)
        {
            String url = graphQlBuilder.buildUrl(ProductTypeEnum.PS5, PlatformEnum.PS5, SortByEnum.BESTSELLING, GenreEnum.ADVENTURE, 51, i);
            String rawJson = restApiClient.getData(url, Regions.US.getLocaleHeaderCode());
            List<String> list = catalogDataFetcher.getListOfProductsId(rawJson);
            String id = list.get(3);
            ProductResponse productResponse = productDataFetcher.getProductResponse(Regions.US.getRegionCode(), id);
            ProductBatch batch = batchBuilder.buildBatch(productResponse);
            batchSaver.saveBatchToDb(batch);
        }
    }
}
