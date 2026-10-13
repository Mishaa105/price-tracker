package io.github.Mishaa105.price_tracker.infrastructure.client;

import io.github.Mishaa105.price_tracker.constants.GraphQlCategoryGridConstants;
import io.github.Mishaa105.price_tracker.constants.GraphQlSearchConstants;
import io.github.Mishaa105.price_tracker.dto.graphql.*;
import io.github.Mishaa105.price_tracker.dto.graphql.products.PageArgs;
import io.github.Mishaa105.price_tracker.dto.graphql.products.ProductVariables;
import io.github.Mishaa105.price_tracker.dto.graphql.products.SortBy;
import io.github.Mishaa105.price_tracker.dto.graphql.search.SearchVariables;
import io.github.Mishaa105.price_tracker.enums.graphql.GenreEnum;
import io.github.Mishaa105.price_tracker.enums.graphql.PlatformEnum;
import io.github.Mishaa105.price_tracker.enums.graphql.ProductTypeEnum;
import io.github.Mishaa105.price_tracker.enums.graphql.SortByEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class GraphQlUrlBuilder
{
    private final ObjectMapper mapper;

    public String buildProductsUrl(ProductTypeEnum productType, PlatformEnum platform, SortByEnum sortType, GenreEnum genre, int size, int offset)
    {
        String baseUrl = GraphQlCategoryGridConstants.BASE_URL;
        String operationName = GraphQlCategoryGridConstants.OPERATION_NAME;
        int version = GraphQlCategoryGridConstants.VERSION;
        String hash = GraphQlCategoryGridConstants.HASH;

        PageArgs pageArgs = new PageArgs(size, offset);
        SortBy sortBy = new SortBy(sortType.getSortType(), sortType.isAscending());
        List<String> filterBy = List.of(genre.getGenre(), platform.getPlatform());
        List<String> facetOptions = Collections.emptyList();

        PersistedQuery persistedQuery = new PersistedQuery(version, hash);

        ProductVariables productVariables = new ProductVariables(productType.getId(), pageArgs, sortBy, filterBy, facetOptions);
        Extensions extensions = new Extensions(persistedQuery);

        String variablesJson = mapper.writeValueAsString(productVariables);
        String extensionsJson = mapper.writeValueAsString(extensions);

        String encodedVariables = URLEncoder.encode(variablesJson, StandardCharsets.UTF_8);
        String encodedExtensions = URLEncoder.encode(extensionsJson, StandardCharsets.UTF_8);

        String url = baseUrl
                + "?operationName=" + operationName
                + "&variables=" + encodedVariables
                + "&extensions=" + encodedExtensions;

        log.debug("GraphQL products URL: {}", url);

        return url;
    }

    public String buildSearchUrl(String query)
    {
        String baseUrl = GraphQlSearchConstants.BASE_URL;
        int pageSize = GraphQlSearchConstants.PAGE_SIZE;
        int pageOffset = GraphQlSearchConstants.PAGE_OFFSET;
        String operationName = GraphQlSearchConstants.OPERATION_NAME;
        String nextCursor = GraphQlSearchConstants.NEXT_CURSOR;
        String countryCode = GraphQlSearchConstants.COUNTRY_CODE;
        String languageCode = GraphQlSearchConstants.LANGUAGE_CODE;
        String hash = GraphQlSearchConstants.HASH;
        int version = GraphQlSearchConstants.VERSION;

        PersistedQuery persistedQuery = new PersistedQuery(version, hash);

        SearchVariables variables = new SearchVariables(countryCode, languageCode, nextCursor, pageOffset, pageSize, query);
        Extensions extensions = new Extensions(persistedQuery);

        String variablesJson = mapper.writeValueAsString(variables);
        String extensionsJson = mapper.writeValueAsString(extensions);

        String encodedVariables = URLEncoder.encode(variablesJson, StandardCharsets.UTF_8);
        String encodedExtensions = URLEncoder.encode(extensionsJson, StandardCharsets.UTF_8);

        String url = baseUrl
                + "?operationName=" + operationName
                + "&variables=" + encodedVariables
                + "&extensions=" + encodedExtensions;

        log.debug("GraphQL search URL: {}", url);

        return url;
    }
}
