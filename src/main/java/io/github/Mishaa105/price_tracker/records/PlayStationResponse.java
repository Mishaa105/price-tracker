package io.github.Mishaa105.price_tracker.records;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PlayStationResponse(Cache cache) {}
