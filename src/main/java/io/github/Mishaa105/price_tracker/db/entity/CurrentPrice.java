package io.github.Mishaa105.price_tracker.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "current_prices")
@Getter
@Setter
@NoArgsConstructor
public class CurrentPrice
{
    public CurrentPrice(Integer originalPrice, Integer discountPrise, String offerBranding, String priceCurrencyCode)
    {
        this.originalPrice = originalPrice;
        this.discountPrise = discountPrise;
        this.offerBranding = offerBranding;
        this.priceCurrencyCode = priceCurrencyCode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private Integer originalPrice;

    @Column
    private Integer discountPrise;

    @Column
    private String offerBranding;

    @Column
    private String priceCurrencyCode;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
