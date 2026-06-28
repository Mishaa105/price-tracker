package io.github.Mishaa105.price_tracker.records.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Local(TelemetryMeta telemetryMeta, String lowestRecentPrice, String discountBadgeText,
                    String offerAvailability) {}
