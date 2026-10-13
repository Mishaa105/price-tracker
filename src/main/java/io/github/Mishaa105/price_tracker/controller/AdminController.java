package io.github.Mishaa105.price_tracker.controller;

import io.github.Mishaa105.price_tracker.service.update.ExchangeRateUpdateService;
import io.github.Mishaa105.price_tracker.service.update.PlayStationDataUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/")
public class AdminController
{
    private final PlayStationDataUpdateService playStationDataUpdateService;
    private final ExchangeRateUpdateService exchangeRateUpdateService;

    @PostMapping("jobs/update-prices")
    public void startDataBaseUpdate()
    {
        playStationDataUpdateService.startFullDbUpdate();
    }

    @PostMapping("jobs/update-rates")
    public void startExchangeRatesUpdate()
    {
        exchangeRateUpdateService.startExchangeRateUpdate();
    }
}
