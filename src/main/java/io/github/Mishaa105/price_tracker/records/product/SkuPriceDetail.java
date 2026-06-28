package io.github.Mishaa105.price_tracker.records.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SkuPriceDetail(int originalPriceValue, int discountPriceValue,
                            String priceCurrencyCode, String offerBranding) {}
