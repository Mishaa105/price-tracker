package io.github.Mishaa105.price_tracker.records;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SkuDetail(List<SkuPriceDetail> skuPriceDetail) {}
