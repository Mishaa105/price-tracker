package io.github.Mishaa105.price_tracker.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product
{
    public Product(String productId, String name, String invariantName, String previewUrl,
                   String description, String edition, String releaseDate, Double averageRating, Integer ratingsCount)
    {
        this.productId = productId;
        this.name = name;
        this.invariantName = invariantName;
        this.previewUrl = previewUrl;
        this.description = description;
        this.edition = edition;
        this.releaseDate = releaseDate;
        this.averageRating = averageRating;
        this.ratingsCount = ratingsCount;
    }

    @Id
    private String productId;

    @Column
    private String name;

    @Column
    private String invariantName;

    @Column
    private String previewUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String edition;

    @Column
    private String releaseDate;

    @Column
    private Double averageRating;

    @Column
    private Integer ratingsCount;

    @ManyToOne
    @JoinColumn(name = "store_classification_id")
    private StoreClassification storeClassification;

    @ManyToOne
    @JoinColumn(name = "publisher_name_id")
    private Publisher publisherName;

    @ManyToMany
    @JoinTable(
            name = "product_platforms",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id")
    )
    private Set<Platform> platforms;

    @ManyToMany
    @JoinTable(
            name = "product_languages",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private Set<Language> languages;

    @ManyToMany
    @JoinTable(
            name = "product_genres",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;

    @OneToMany(mappedBy = "product")
    private List<CurrentPrice> currentPrices;

    @OneToMany(mappedBy = "product")
    private List<Price> allPrices;
}
