package io.github.Mishaa105.price_tracker.enums.graphql;

import lombok.Getter;

@Getter
public enum ProductTypeEnum
{
    PS5("d0446d4b-dc9a-4f1e-86ec-651f099c9b29"),
    PS4("30e3fe35-8f2d-4496-95bc-844f56952e3c"),
    ADDONS("51c9aa7a-c0c7-4b68-90b4-328ad11bf42e"),
    DEALS("3f772501-f6f8-49b7-abac-874a88ca4897");

    private final String id;

    ProductTypeEnum(String id)
    {
        this.id = id;
    }
}
