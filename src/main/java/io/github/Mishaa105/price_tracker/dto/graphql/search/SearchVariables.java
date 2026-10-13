package io.github.Mishaa105.price_tracker.dto.graphql.search;

public record SearchVariables(String countryCode, String languageCode, String nextCursor,
                              int pageOffset, int pageSize, String searchTerm) {}
