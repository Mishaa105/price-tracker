package io.github.Mishaa105.price_tracker.dto.allproducts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Concept(List<Product> products, String id)
{
    public List<String> getId()
    {
        if(products == null && id != null && !id.isEmpty())
        {
            return Collections.singletonList(id);
        }
        else if(products != null && !products.isEmpty())
        {
            List<String> id = new ArrayList<>();

            for (Product product : products)
            {
                id.add(product.id());
            }
            return id;
        }
        return null;
    }
}
