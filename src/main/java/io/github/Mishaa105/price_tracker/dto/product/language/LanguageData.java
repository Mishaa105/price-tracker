package io.github.Mishaa105.price_tracker.dto.product.language;

import java.util.Set;

public record LanguageData(Set<SpokenLanguagesByPlatform> spokenLanguagesByPlatform,
                           Set<ScreenLanguagesByPlatform> screenLanguagesByPlatform) {}
