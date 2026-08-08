package io.github.Mishaa105.price_tracker.enums.regions;

import lombok.Getter;

@Getter
public enum Regions
{
    US("/en-us/"),
    TR("/en-tr/"),
    UA("/ru-ua/"),
    IN("/en-in/"),
    PL("/en-pl/");

    private final String regionCode;

    Regions(String regionCode)
    {
        this.regionCode = regionCode;
    }
}
