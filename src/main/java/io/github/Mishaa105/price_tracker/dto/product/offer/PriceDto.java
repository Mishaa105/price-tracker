package io.github.Mishaa105.price_tracker.dto.product.offer;

public record PriceDto(Integer basePriceValue, Integer discountedValue, String currencyCode,
                       String membershipType, String endTime) {}
