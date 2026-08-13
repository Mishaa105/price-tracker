package io.github.Mishaa105.price_tracker.infrastructure.extractor.fetcher;

import io.github.Mishaa105.price_tracker.dto.catalog.CatalogResponse;
import io.github.Mishaa105.price_tracker.dto.catalog.Concept;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CatalogDataFetcher
{
    private final ObjectMapper mapper;

    public List<String> getListOfProductsId(String rawJson)
    {
        CatalogResponse catalogResponse = mapper.readValue(rawJson, CatalogResponse.class);
        List<Concept> listOfProduct = catalogResponse.data().categoryGridRetrieve().getListOfProducts();
        List<String> listOfId = new ArrayList<>();
        for (Concept product : listOfProduct)
        {
            if (product.getProductsId() != null)
            {
                listOfId.addAll(product.getProductsId());
            }
        }
        log.info("Лист с {} айди получен", listOfId.size());
        log.debug("Лист айди: {}", listOfId);
        return listOfId;
    }
}
