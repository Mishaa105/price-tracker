package io.github.Mishaa105.price_tracker.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Local(TelemetryMeta telemetryMeta, String offerAvailability, CtaDataTrack ctaDataTrack) {}
