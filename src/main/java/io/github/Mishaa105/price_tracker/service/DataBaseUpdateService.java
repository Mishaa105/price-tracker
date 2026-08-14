package io.github.Mishaa105.price_tracker.service;

import io.github.Mishaa105.price_tracker.config.Executor;
import io.github.Mishaa105.price_tracker.dto.product.ProductResponse;
import io.github.Mishaa105.price_tracker.enums.graphql.GenreEnum;
import io.github.Mishaa105.price_tracker.enums.graphql.PlatformEnum;
import io.github.Mishaa105.price_tracker.enums.graphql.ProductTypeEnum;
import io.github.Mishaa105.price_tracker.enums.graphql.SortByEnum;
import io.github.Mishaa105.price_tracker.enums.regions.Regions;
import io.github.Mishaa105.price_tracker.infrastructure.batch.BatchBuilder;
import io.github.Mishaa105.price_tracker.infrastructure.batch.BatchSaver;
import io.github.Mishaa105.price_tracker.infrastructure.batch.ProductAggregate;
import io.github.Mishaa105.price_tracker.infrastructure.client.GraphQlUrlBuilder;
import io.github.Mishaa105.price_tracker.infrastructure.client.RestApiClient;
import io.github.Mishaa105.price_tracker.infrastructure.extractor.fetcher.CatalogDataFetcher;
import io.github.Mishaa105.price_tracker.infrastructure.extractor.fetcher.ProductDataFetcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

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
    private final Executor executor;

    public void startFullDbUpdate()
    {
        int pageSize = 1000;
        SortByEnum sortType = SortByEnum.NAME_A_Z;
        List<String> listOfConceptsId = new ArrayList<>();
        List<String> listOfErrorId = Collections.synchronizedList(new ArrayList<>());

        for (Regions region : Regions.values())
        {
            for (ProductTypeEnum productType : ProductTypeEnum.values())
            {
                for (PlatformEnum platform : PlatformEnum.values())
                {
                    for (GenreEnum genre : GenreEnum.values())
                    {
                        int offset = 0;
                        while (true)
                        {
                            String url = graphQlBuilder.buildUrl(productType, platform, sortType, genre, pageSize, offset);
                            String rawJson = restApiClient.getData(url, region.getLocaleHeaderCode());
                            List<String> listOfId = catalogDataFetcher.getListOfProductsId(rawJson);
                            List<ProductResponse> productResponseList = Collections.synchronizedList(new ArrayList<>());
                            List<CompletableFuture<Void>> futures = new ArrayList<>();
                            Semaphore semaphore = new Semaphore(10);

                            if (listOfId.isEmpty())
                            {
                                break;
                            }

                            for (String id : listOfId)
                            {
                                if (id.length() < 12)
                                {
                                    listOfConceptsId.add(id);
                                    continue;
                                }

                                CompletableFuture<Void> future = CompletableFuture.runAsync(() ->
                                {
                                    try
                                    {
                                        semaphore.acquire();
                                        Thread.sleep((long) (Math.random() * 100 + 50));
                                        ProductResponse productResponse = productDataFetcher.getProductResponse(region.getRegionCode(), id);
                                        if (productResponse != null)
                                        {
                                            productResponseList.add(productResponse);
                                        }
                                        else
                                        {
                                            log.warn("Сервер вернул null для id {}", id);
                                            listOfErrorId.add(id);
                                        }
                                    }
                                    catch (Exception e)
                                    {
                                        log.error("Ошибка при обработке id {}", id);
                                        listOfErrorId.add(id);
                                    }
                                    finally
                                    {
                                        semaphore.release();
                                    }
                                }, executor.virtualThreadExecutor());
                                futures.add(future);
                            }

                            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

                            List<ProductAggregate> batch = batchBuilder.buildBatch(productResponseList);
                            batchSaver.saveBatchToDb(batch);
                            productResponseList.clear();

                            offset += pageSize;
                        }
                    }
                }
            }
        }
        log.info("error: {}", listOfErrorId.size());
        log.info("concept: {}", listOfConceptsId.size());
    }
}
