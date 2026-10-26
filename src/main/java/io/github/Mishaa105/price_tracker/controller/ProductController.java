package io.github.Mishaa105.price_tracker.controller;

import io.github.Mishaa105.price_tracker.service.product_page.ProductPageService;
import io.github.Mishaa105.price_tracker.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController
{
    private final SearchService searchService;
    private final ProductPageService productPageService;

    @GetMapping("/search")
    List<String> search(@RequestParam String query)
    {
        return searchService.search(query);
    }

    @GetMapping("/page")
    String getProductPage(@RequestParam String productId)
    {
        return productPageService.getProductPage(productId);
    }
}
