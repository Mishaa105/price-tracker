package io.github.Mishaa105.price_tracker.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GameName(String invariantName, String name) {}
