package io.github.Mishaa105.price_tracker.enums.regions;

import lombok.Getter;

@Getter
public enum Regions
{
    US("/en-us/", "en-US"),
    TR("/en-tr/", "en-TR"),
    UA("/ru-ua/", "ru-UA"),
    IN("/en-in/", "en-IN"),
    PL("/en-pl/", "en-PL");

    private final String regionCode;
    private final String localeHeaderCode;

    Regions(String regionCode, String localeHeaderCode)
    {
        this.regionCode = regionCode;
        this.localeHeaderCode = localeHeaderCode;
    }
}
