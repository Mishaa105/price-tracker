package io.github.Mishaa105.price_tracker.controller;

import io.github.Mishaa105.price_tracker.service.main_page.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/homepage")
public class MainPageController
{
    private final MainPageService mainPageService;

    @GetMapping
    String getMainPage()
    {
        return mainPageService.getMainPage();
    }
}
