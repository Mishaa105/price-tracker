package io.github.Mishaa105.price_tracker.dto.product;

public record SkuPriceDetail(Integer originalPriceValue, Integer discountPriceValue,
                            String priceCurrencyCode, String offerBranding) {}
