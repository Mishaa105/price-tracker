package io.github.Mishaa105.price_tracker.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product
{
    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String invariantName;

    @Column
    private String previewUrl;

    @OneToMany(mappedBy = "product")
    private List<CurrentPrice> currentPrices;

    @OneToMany(mappedBy = "product")
    private List<Price> allPrices;
}
