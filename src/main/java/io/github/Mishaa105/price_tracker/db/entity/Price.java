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
    public Price(Integer originalPrice, Integer discountPrice)
    {
        this.originalPrice = originalPrice;
        this.discountPrice = discountPrice;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private Integer originalPrice;

    @Column
    private Integer discountPrice;

    @ManyToOne
    @JoinColumn(name = "branding_id")
    private Brand offerBrand;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency priceCurrencyCode;

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
