package io.github.Mishaa105.price_tracker.enums.graphql;

import lombok.Getter;

@Getter
public enum Platform
{
    PS5("targetPlatforms:PS5"),
    PS4("targetPlatforms:PS4");

    private final String platform;

    Platform(String platform)
    {
        this.platform = platform;
    }
}
