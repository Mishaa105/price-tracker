package io.github.Mishaa105.price_tracker.dto.allproducts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Product(String id) {}
