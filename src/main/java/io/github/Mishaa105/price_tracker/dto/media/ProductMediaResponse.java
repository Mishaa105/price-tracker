package io.github.Mishaa105.price_tracker.dto.media;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public record ProductMediaResponse(MediaCache cache)
{
    public String getPreviewUrl()
    {
        if(cache != null && cache.mediaData() != null && cache.mediaData().media() != null && !cache.mediaData().media().isEmpty())
        {
            return cache.mediaData().media().getLast().url();
        }

        return null;
    }

    public Set<String> getGenres()
    {
        return Optional.ofNullable(cache).map(MediaCache::mediaData)
                .map(MediaData::localizedGenres).map(set -> set.stream()
            .map(LocalizedGenres::value).collect(Collectors.toSet())).orElse(Collections.emptySet());
    }
}
