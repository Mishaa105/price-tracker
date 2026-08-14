package io.github.Mishaa105.price_tracker.dto.product.offer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record ProductOfferResponse(Args args, OfferCache cache)
{
    public String getName()
    {
        return Optional.ofNullable(cache).map(OfferCache::gameName).map(GameName::name).orElse(null);
    }

    public String getInvariantName()
    {
        return Optional.ofNullable(cache).map(OfferCache::gameName).map(GameName::invariantName).orElse(null);
    }

    public String getId()
    {
        return Optional.ofNullable(args).map(Args::productId).orElse(null);
    }

    public List<PriceDto> getListOfAvailablePriceData()
    {
        List<PriceDto> prices = new ArrayList<>();

        if (cache == null)
        {
            return prices;
        }

        return cache.basePrice().stream().map(PriceData::price).toList();
    }
}
