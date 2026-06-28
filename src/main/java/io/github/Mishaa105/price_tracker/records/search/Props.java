package io.github.Mishaa105.price_tracker.records.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Props(ApolloState apolloState) {}
