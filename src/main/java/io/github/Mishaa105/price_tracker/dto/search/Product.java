package io.github.Mishaa105.price_tracker.dto.search;

import java.util.List;

public record Product(String name, String storeDisplayClassification, List<String> platforms,
                      String id, List<Media> media) {}
