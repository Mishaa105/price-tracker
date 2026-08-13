package io.github.Mishaa105.price_tracker.dto.catalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record Concept(List<Product> products, String id)
{
    public List<String> getProductsId()
    {
        if(products != null && !products.isEmpty())
        {
            List<String> ids = new ArrayList<>();

            for (Product product : products)
            {
                if (product != null && product.id() != null)
                {
                    ids.add(product.id());
                }
            }
            return ids;
        }

        if (id != null && !id.isEmpty())
        {
            return Collections.singletonList(id);
        }
        return Collections.emptyList();
    }
}
