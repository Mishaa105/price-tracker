package io.github.Mishaa105.price_tracker.dto.graphql.products;

import java.util.List;

public record ProductVariables(String id, PageArgs pageArgs, SortBy sortBy, List<String> filterBy, List<String> facetOptions) {}
