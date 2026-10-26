package io.github.Mishaa105.price_tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "currencies")
@Getter
@Setter
@NoArgsConstructor
public class Currency
{
    public Currency(String currency)
    {
        this.currency = currency;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String currency;

    @Column(name = "exchange_rate", precision = 12, scale = 4)
    private BigDecimal exchangeRate;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "priceCurrencyCode")
    private List<CurrentPrice> currentPrices;

    @OneToMany(mappedBy = "priceCurrencyCode")
    private List<Price> allPrices;
}
