package io.github.Mishaa105.price_tracker.dto.product.language;

import io.github.Mishaa105.price_tracker.entity.Platform;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public record ProductLanguagesResponse(LanguageCache cache)
{
    public Set<String> getSpokenLanguagesByPlatform(Platform platform)
    {
        return Optional.ofNullable(cache).map(LanguageCache::languageData).map(LanguageData::spokenLanguagesByPlatform)
                .flatMap(list -> list.stream()
                        .filter(spokenLanguagesByPlatform
                                -> platform.getPlatform().equals(spokenLanguagesByPlatform.platform()))
                        .map(SpokenLanguagesByPlatform::spokenLanguages).findFirst()).orElse(Collections.emptySet());
    }

    public Set<String> getScreenLanguagesByPlatform(Platform platform)
    {
        return Optional.ofNullable(cache).map(LanguageCache::languageData).map(LanguageData::screenLanguagesByPlatform)
                .flatMap(set -> set.stream()
                        .filter(screenLanguagesByPlatform
                                -> platform.getPlatform().equals(screenLanguagesByPlatform.platform()))
                        .map(ScreenLanguagesByPlatform::screenLanguages).findFirst()).orElse(Collections.emptySet());
    }
}
