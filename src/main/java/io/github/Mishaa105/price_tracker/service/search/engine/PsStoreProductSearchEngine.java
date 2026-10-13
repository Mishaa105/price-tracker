package io.github.Mishaa105.price_tracker.service.search.engine;

import io.github.Mishaa105.price_tracker.dto.search.PlayStationSearchResponse;
import io.github.Mishaa105.price_tracker.dto.search.Results;
import io.github.Mishaa105.price_tracker.infrastructure.client.GraphQlUrlBuilder;
import io.github.Mishaa105.price_tracker.infrastructure.client.PlayStationClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PsStoreProductSearchEngine implements ProductSearchEngine
{
    private final GraphQlUrlBuilder graphQlUrlBuilder;
    private final PlayStationClient playStationClient;
    private final ObjectMapper mapper;

    @Override
    public List<String> search(String query)
    {
        log.info("Поиск через PlayStation Store");
        List<String> productsIds = new ArrayList<>(List.of());
        String regionCode = "en-US";

        String url = graphQlUrlBuilder.buildSearchUrl(query);
        String rawJson = playStationClient.getData(url, regionCode);
        PlayStationSearchResponse searchResponse = mapper.readValue(rawJson, PlayStationSearchResponse.class);
        List<Results> results = searchResponse.data().universalSearch().results();

        for (Results result : results)
        {
            productsIds.add(result.id());
        }

        log.debug("Список найденных ID: {}", productsIds);

        return productsIds;
    }
}
