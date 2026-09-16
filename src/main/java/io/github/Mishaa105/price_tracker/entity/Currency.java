package io.github.Mishaa105.price_tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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

    @OneToMany(mappedBy = "priceCurrencyCode")
    private List<CurrentPrice> currentPrices;

    @OneToMany(mappedBy = "priceCurrencyCode")
    private List<Price> allPrices;
}
