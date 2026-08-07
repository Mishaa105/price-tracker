package io.github.Mishaa105.price_tracker.dto.media;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MediaData(List<Media> media) {}
