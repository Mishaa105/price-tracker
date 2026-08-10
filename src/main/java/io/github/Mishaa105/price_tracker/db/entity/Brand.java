package io.github.Mishaa105.price_tracker.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "brands")
@Getter
@Setter
@NoArgsConstructor
public class Brand
{
    public Brand(String brand)
    {
        this.brand = brand;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String brand;

    @OneToMany(mappedBy = "offerBrand")
    private List<CurrentPrice> currentPrices;

    @OneToMany(mappedBy = "offerBrand")
    private List<Price> allPrices;
}
