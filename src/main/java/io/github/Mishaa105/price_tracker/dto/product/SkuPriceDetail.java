package io.github.Mishaa105.price_tracker.dto.product;

public record SkuPriceDetail(int originalPriceValue, int discountPriceValue,
                            String priceCurrencyCode, String offerBranding) {}
