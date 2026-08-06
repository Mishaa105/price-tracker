package io.github.Mishaa105.price_tracker.service;

import io.github.Mishaa105.price_tracker.dto.allproducts.AllProducts;
import io.github.Mishaa105.price_tracker.dto.allproducts.Concept;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataBaseUpdateService
{
    private final ObjectMapper mapper;

    private List<String> getListOfProductsId(String rawJson)
    {
        AllProducts batch = mapper.readValue(rawJson, AllProducts.class);
        List<Concept> listOfProduct = batch.data().categoryGridRetrieve().getListOfProducts();
        List<String> listOfId = new ArrayList<>();
        for (Concept product : listOfProduct)
        {
            if(product.getId() != null)
            {
                listOfId.addAll(product.getId());
            }
        }
        return listOfId;
    }

    public List<String> test(String string)
    {
        return getListOfProductsId(string);
    }

}
