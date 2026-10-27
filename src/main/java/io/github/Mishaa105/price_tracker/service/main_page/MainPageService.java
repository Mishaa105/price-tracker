package io.github.Mishaa105.price_tracker.service.main_page;

import io.github.Mishaa105.price_tracker.dto.main_page.CurrentPriceDto;
import io.github.Mishaa105.price_tracker.dto.main_page.MainPageProduct;
import io.github.Mishaa105.price_tracker.infrastructure.db.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MainPageService
{
    private final ObjectMapper mapper;
    private final ProductRepository productRepository;

    private List<MainPageProduct> mainPageBuilder()
    {
        String yearAgoDate = Instant.now().minus(365, ChronoUnit.DAYS).toString();
        List<String> productIds = productRepository.getProductIdsForMainPage(yearAgoDate);
        List<MainPageProduct> mainPageProductList = new ArrayList<>();

        for (String productId : productIds)
        {
            String name = productRepository.getProductNameById(productId);
            String preview = productRepository.getProductPreviewById(productId);
            String edition = productRepository.getProductEditionById(productId);
            List<String> platforms = productRepository.getProductPlatformsById(productId);
            List<CurrentPriceDto> currentPrices = productRepository.getProductCurrentPricesForMainPage(productId);

            MainPageProduct mainPageProduct = new MainPageProduct(name, productId, preview, edition, platforms, currentPrices);
            mainPageProductList.add(mainPageProduct);
        }

        return mainPageProductList;
    }

    private String mainPageToJson(List<MainPageProduct> mainPageProductList)
    {
        return mapper.writeValueAsString(mainPageProductList);
    }

    public String getMainPage()
    {
        List<MainPageProduct> mainPageProductList = mainPageBuilder();
        return mainPageToJson(mainPageProductList);
    }
}
