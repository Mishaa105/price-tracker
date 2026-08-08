package io.github.Mishaa105.price_tracker.dto.graphql;

import java.util.List;

public record Variables(String id, PageArgs pageArgs, SortBy sortBy, List<String> filterBy, List<String> facetOptions) {}
