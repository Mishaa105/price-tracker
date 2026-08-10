package io.github.Mishaa105.price_tracker.dto.metadata;

import java.util.List;
import java.util.Set;

public record Metadata(Edition edition, Set<String> platforms, String storeDisplayClassification,
                       String releaseDate, String publisherName, StarRating starRating,
                       List<Description> descriptions) {}
