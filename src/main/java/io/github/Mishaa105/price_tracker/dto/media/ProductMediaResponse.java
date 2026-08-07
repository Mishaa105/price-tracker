package io.github.Mishaa105.price_tracker.dto.media;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductMediaResponse(Cache cache)
{
    public String getPreviewUrl()
    {
        return cache.mediaData().media().getLast().url();
    }
}
