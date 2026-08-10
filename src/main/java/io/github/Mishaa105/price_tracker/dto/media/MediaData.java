package io.github.Mishaa105.price_tracker.dto.media;

import java.util.List;
import java.util.Set;

public record MediaData(List<Media> media, Set<LocalizedGenres> localizedGenres) {}
