package io.github.Mishaa105.price_tracker.batch;

import io.github.Mishaa105.price_tracker.db.entity.CurrentPrice;
import io.github.Mishaa105.price_tracker.db.entity.Offer;
import io.github.Mishaa105.price_tracker.db.entity.Price;
import io.github.Mishaa105.price_tracker.db.entity.Product;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductBatch
{
    private final List<Product> products = new ArrayList<>();
    private final List<Offer> offers = new ArrayList<>();
    private final List<CurrentPrice> currentPrices = new ArrayList<>();
    private final List<Price> prices = new ArrayList<>();
}
