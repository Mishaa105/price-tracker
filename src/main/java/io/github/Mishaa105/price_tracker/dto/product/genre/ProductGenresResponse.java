package io.github.Mishaa105.price_tracker.dto.product.genre;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public record ProductGenresResponse(GenreCache cache)
{
    public Set<String> getGenres()
    {
        return Optional.ofNullable(cache).map(GenreCache::genreData)
                .map(GenreData::localizedGenres).map(set -> set.stream()
            .map(LocalizedGenres::value).collect(Collectors.toSet())).orElse(Collections.emptySet());
    }
}
