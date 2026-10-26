package io.github.Mishaa105.price_tracker.dto.product_page;

public record CurrentPricePoint(Integer discountPrice, Integer originalPrice, String brand, String currency,
                                String offerEndDate, String offerStartDate) {}
