package io.github.Mishaa105.price_tracker.dto.product.metadata;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public record ProductMetadataResponse(MetadataCache cache)
{
    public String getEdition()
    {
        return Optional.ofNullable(cache).map(MetadataCache::metadata).map(Metadata::edition).map(Edition::name).orElse(null);
    }

    public Set<String> getPlatforms()
    {
        return Optional.ofNullable(cache).map(MetadataCache::metadata).map(Metadata::platforms)
                .orElse(Collections.emptySet());
    }

    public String getStoreDisplayClassification()
    {
        return Optional.ofNullable(cache).map(MetadataCache::metadata).map(Metadata::storeDisplayClassification).orElse(null);
    }

    public String getReleaseDate()
    {
        return Optional.ofNullable(cache).map(MetadataCache::metadata).map(Metadata::releaseDate).orElse(null);
    }

    public String getPublisherName()
    {
        return Optional.ofNullable(cache).map(MetadataCache::metadata).map(Metadata::publisherName).orElse(null);
    }

    public Double getAverageRating()
    {
        return Optional.ofNullable(cache).map(MetadataCache::metadata).map(Metadata::starRating).map(StarRating::averageRating).orElse(null);
    }

    public Integer getTotalRatingsCount()
    {
        return Optional.ofNullable(cache).map(MetadataCache::metadata).map(Metadata::starRating).map(StarRating::totalRatingsCount).orElse(null);
    }

    public String getLongDescription()
    {
        return Optional.ofNullable(cache).map(MetadataCache::metadata).map(Metadata::descriptions)
                .flatMap(list -> list.stream()
                        .filter(description -> "LONG".equals(description.type()))
                        .map(Description::value).findFirst()).orElse(null);
    }
}
