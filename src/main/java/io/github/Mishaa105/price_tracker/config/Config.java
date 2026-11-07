package io.github.Mishaa105.price_tracker.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app")
public record Config(@NotNull Playstation playstation, @NotNull Jwt jwt)
{
    public record Playstation (@NotBlank String baseUrl) {}

    public record Jwt (@NotBlank String secretKey) {}
}
