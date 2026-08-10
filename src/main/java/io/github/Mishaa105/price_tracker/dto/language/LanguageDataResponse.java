package io.github.Mishaa105.price_tracker.dto.language;

import io.github.Mishaa105.price_tracker.db.entity.Platform;

import java.util.Optional;
import java.util.Set;

public record LanguageDataResponse(LanguageCache cache)
{
    public Set<String> getSpokenLanguagesByPlatform(Platform platform)
    {
        return Optional.ofNullable(cache).map(LanguageCache::languageData).map(LanguageData::spokenLanguagesByPlatform)
                .flatMap(list -> list.stream()
                        .filter(spokenLanguagesByPlatform
                                -> platform.getPlatform().equals(spokenLanguagesByPlatform.platform()))
                        .map(SpokenLanguagesByPlatform::spokenLanguages).findFirst()).orElse(null);
    }

    public Set<String> getScreenLanguagesByPlatform(Platform platform)
    {
        return Optional.ofNullable(cache).map(LanguageCache::languageData).map(LanguageData::screenLanguagesByPlatform)
                .flatMap(list -> list.stream()
                        .filter(screenLanguagesByPlatform
                                -> platform.getPlatform().equals(screenLanguagesByPlatform.platform()))
                        .map(ScreenLanguagesByPlatform::screenLanguages).findFirst()).orElse(null);
    }
}
