package io.github.Mishaa105.price_tracker.dto.catalog;

import java.util.Collections;
import java.util.List;

public record CategoryGridRetrieve(List<Concept> concepts, List<Concept> products)
{
    public List<Concept> getListOfProducts()
    {
        if(concepts != null && !concepts.isEmpty())
        {
            return concepts;
        }
        else if(products != null &&  !products.isEmpty())
        {
            return products;
        }

        return Collections.emptyList();
    }
}
