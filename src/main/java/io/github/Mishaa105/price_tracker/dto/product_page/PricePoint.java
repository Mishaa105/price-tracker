package io.github.Mishaa105.price_tracker.dto.product_page;

import java.time.LocalDateTime;

public record PricePoint(LocalDateTime date, Integer discountPrice, Integer originalPrice, String brand, String currency,
                         String offerEndDate, String offerStartDate) {}
