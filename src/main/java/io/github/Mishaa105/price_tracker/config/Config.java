package io.github.Mishaa105.price_tracker.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app")
public record Config(@NotNull Playstation playstation)
{
    public record Playstation (@NotBlank String baseUrl) {}
}
