package io.github.Mishaa105.price_tracker.service.search.engine;

import io.github.Mishaa105.price_tracker.infrastructure.db.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class DbProductSearchEngine implements ProductSearchEngine
{
    private final ProductRepository productRepository;

    @Override
    public List<String> search(String query)
    {
        log.info("Поиск по базе данных");

        List<String> productsList = productRepository.getProductsIdByName(query);

        log.debug("Список найденных ID: {}", productsList);

        return productsList;
    }
}
