package io.github.Mishaa105.price_tracker.records.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BasePriceData(Local local) {}
