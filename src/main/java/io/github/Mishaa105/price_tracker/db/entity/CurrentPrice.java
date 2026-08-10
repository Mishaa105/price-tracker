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
    public CurrentPrice(Integer originalPrice, Integer discountPrise)
    {
        this.originalPrice = originalPrice;
        this.discountPrise = discountPrise;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private Integer originalPrice;

    @Column
    private Integer discountPrise;

    @ManyToOne
    @JoinColumn(name = "branding_id")
    private Brand offerBrand;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency priceCurrencyCode;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
