package io.github.Mishaa105.price_tracker.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "all_prices")
@Getter
@Setter
@NoArgsConstructor
public class Price
{
    public Price(Integer originalPrice, Integer discountPrise, String offerBranding, String priceCurrencyCode)
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

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime savingTime;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
