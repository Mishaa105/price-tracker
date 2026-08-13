package io.github.Mishaa105.price_tracker.dto.product.media;

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
}
