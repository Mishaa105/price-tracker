package io.github.Mishaa105.price_tracker.dto.main;

public record Price(Integer basePriceValue, Integer discountedValue, String currencyCode,
                    String membershipType, String endTime) {}
