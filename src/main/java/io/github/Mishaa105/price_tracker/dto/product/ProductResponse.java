package io.github.Mishaa105.price_tracker.dto.product;

import io.github.Mishaa105.price_tracker.dto.product.genre.ProductGenresResponse;
import io.github.Mishaa105.price_tracker.dto.product.language.ProductLanguagesResponse;
import io.github.Mishaa105.price_tracker.dto.product.media.ProductMediaResponse;
import io.github.Mishaa105.price_tracker.dto.product.metadata.ProductMetadataResponse;
import io.github.Mishaa105.price_tracker.dto.product.offer.ProductOfferResponse;

public record ProductResponse(ProductOfferResponse offerResponse, ProductMediaResponse mediaResponse,
                              ProductMetadataResponse metadataResponse, ProductGenresResponse genresResponse,
                              ProductLanguagesResponse languagesResponse) {}
