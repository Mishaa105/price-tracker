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

    public List<Local> getListOfAvailableLocals()
    {
        List<Local> locals = new ArrayList<>();

        if (cache == null)
        {
            return locals;
        }

        if (cache.basePrice() != null && cache.basePrice().local() != null)
        {
            locals.add(cache.basePrice().local());
        }

        if (cache.preorderPrice() != null && cache.preorderPrice().local() != null)
        {
            locals.add(cache.preorderPrice().local());
        }

        if (cache.freePrice() != null && cache.freePrice().local() != null)
        {
            locals.add(cache.freePrice().local());
        }

        if (cache.subscriptionPrice() != null && cache.subscriptionPrice().local() != null)
        {
            locals.add(cache.subscriptionPrice().local());
        }

        if (cache.exclusiveDiscountData() != null)
        {
            for (ExclusiveDiscountData data : cache.exclusiveDiscount())
            {
                if(data != null && data.local() != null)
                {
                    locals.add(data.local());
                }
            }
        }

        return locals;
    }
}
