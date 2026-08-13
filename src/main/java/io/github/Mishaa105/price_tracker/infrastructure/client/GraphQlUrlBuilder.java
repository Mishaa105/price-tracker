package io.github.Mishaa105.price_tracker.infrastructure.client;

import io.github.Mishaa105.price_tracker.constants.GraphQlCategoryGridConstants;
import io.github.Mishaa105.price_tracker.dto.graphql.*;
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

    public String buildUrl(ProductTypeEnum productType, PlatformEnum platform, SortByEnum sortType, GenreEnum genre, int size, int offset)
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

        Variables variables = new Variables(productType.getId(), pageArgs, sortBy, filterBy, facetOptions);
        Extensions extensions = new Extensions(persistedQuery);

        String variablesJson = mapper.writeValueAsString(variables);
        String extensionsJson = mapper.writeValueAsString(extensions);

        String encodedVariables = URLEncoder.encode(variablesJson, StandardCharsets.UTF_8);

        String encodedExtensions = URLEncoder.encode(extensionsJson, StandardCharsets.UTF_8);

        String url = baseUrl
                + "?operationName=" + operationName
                + "&variables=" + encodedVariables
                + "&extensions=" + encodedExtensions;

        log.debug("GraphQL URL: {}", url);

        return url;
    }
}
