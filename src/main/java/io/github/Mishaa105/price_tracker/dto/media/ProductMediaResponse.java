package io.github.Mishaa105.price_tracker.dto.media;

public record ProductMediaResponse(Cache cache)
{
    public String getPreviewUrl()
    {
        if(cache != null && cache.mediaData() != null && cache.mediaData().media() != null && !cache.mediaData().media().isEmpty())
        {
            return cache.mediaData().media().getLast().url();
        }

        return null;
    }
}
