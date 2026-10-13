package io.github.Mishaa105.price_tracker.service.search;

import io.github.Mishaa105.price_tracker.service.search.engine.ProductSearchEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class SearchService implements ProductSearchEngine
{
    private final ProductSearchEngine psStoreProductSearchEngine;
    private final ProductSearchEngine dbProductSearchEngine;

    @Override
    public List<String> search(String query)
    {
        try
        {
            return psStoreProductSearchEngine.search(query);
        }
        catch (Exception e)
        {
            log.warn("Ошибка при поиске через PS store engine: {}. Используем db engine ", e.getMessage());
            return dbProductSearchEngine.search(query);
        }
    }
}
