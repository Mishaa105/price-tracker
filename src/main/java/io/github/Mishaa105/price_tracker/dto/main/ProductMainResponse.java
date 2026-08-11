package io.github.Mishaa105.price_tracker.dto.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record ProductMainResponse(Args args, ProductCache cache)
{
    public String getName()
    {
        return Optional.ofNullable(cache).map(ProductCache::gameName).map(GameName::name).orElse(null);
    }

    public String getInvariantName()
    {
        return Optional.ofNullable(cache).map(ProductCache::gameName).map(GameName::invariantName).orElse(null);
    }

    public String getId()
    {
        return Optional.ofNullable(args).map(Args::productId).orElse(null);
    }

    public List<Price> getListOfAvailablePriceData()
    {
        List<Price> prices = new ArrayList<>();

        if (cache == null)
        {
            return prices;
        }

        return cache.basePrice().stream().map(PriceData::price).toList();
    }
}
