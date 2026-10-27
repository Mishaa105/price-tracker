package io.github.Mishaa105.price_tracker.dto.main_page;

import java.util.List;

public record MainPageProduct(String name, String productId, String preview, String edition, List<String> platforms, List<CurrentPriceDto> currentPrices) {}
