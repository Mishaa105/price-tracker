package io.github.Mishaa105.price_tracker.enums.graphql;

import lombok.Getter;

@Getter
public enum SortByEnum
{
    BESTSELLING("sales30", false),
    MOST_DOWNLOADED("downloads30", false),
    NAME_A_Z("productName", true),
    NAME_Z_A("productName", false),
    RELEASE_DATE_OLD("productReleaseDate", true),
    RELEASE_DATE_NEW("productReleaseDate", false),
    PS_PLUS("contentCollections.contentCollectionStartDate", false);

    private final String sortType;
    private final boolean isAscending;

    SortByEnum(String sortType, boolean isAscending)
    {
        this.sortType = sortType;
        this.isAscending = isAscending;
    }
}
