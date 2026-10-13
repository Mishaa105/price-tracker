package io.github.Mishaa105.price_tracker.controller.admin;

import io.github.Mishaa105.price_tracker.service.ExchangeRateUpdateService;
import io.github.Mishaa105.price_tracker.service.PlayStationDataUpdateService;
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

    @PostMapping("jobs/update-exchange-rates")
    public void startExchangeRatesUpdate()
    {
        exchangeRateUpdateService.startExchangeRateUpdate();
    }
}
