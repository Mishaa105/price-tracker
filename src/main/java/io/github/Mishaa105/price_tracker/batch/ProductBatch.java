package io.github.Mishaa105.price_tracker.batch;

import io.github.Mishaa105.price_tracker.db.entity.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class ProductBatch
{
    private final List<Product> products = new ArrayList<>();
    private final List<Offer> offers = new ArrayList<>();
    private final List<CurrentPrice> currentPrices = new ArrayList<>();
    private final List<Price> prices = new ArrayList<>();
    private final Set<Platform> platforms = new HashSet<>();
    private final List<Publisher> publishers = new ArrayList<>();
    private final List<StoreClassification> storeClassifications = new ArrayList<>();
    private final List<Brand> brands = new ArrayList<>();
    private final List<Currency> currencies = new ArrayList<>();
    private final Set<Language> languages = new HashSet<>();
}
