package io.github.Mishaa105.price_tracker.records.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Product(String name, String storeDisplayClassification, List<String> platforms,
                      String id, List<Media> media) {}
