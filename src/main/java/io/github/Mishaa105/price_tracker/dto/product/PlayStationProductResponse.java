package io.github.Mishaa105.price_tracker.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PlayStationProductResponse(Args args, Cache cache)
{
    public String getName()
    {
        return Optional.ofNullable(cache).map(Cache::gameName).map(GameName::name).orElse(null);
    }

    public String getInvariantName()
    {
        return Optional.ofNullable(cache).map(Cache::gameName).map(GameName::invariantName).orElse(null);
    }

    public String getId()
    {
        return Optional.ofNullable(args).map(Args::productId).orElse(null);
    }

    public List<Local> getListOfAvailablePriceData()
    {
        List<Local> locals = new ArrayList<>();

        if (cache == null)
        {
            return locals;
        }

        return cache.basePrice().stream().map(PriceData::local).toList();
    }
}
