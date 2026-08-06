package io.github.Mishaa105.price_tracker.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "all_prices")
@Getter
@Setter
@NoArgsConstructor
public class Price
{
    @Id
    private Long id;

    @Column
    private Integer originalPrice;

    @Column
    private Integer discountPrise;

    @Column
    private String offerBranding;

    @Column
    private String priceCurrencyCode;

    @Column
    private String discountBadgeText;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
