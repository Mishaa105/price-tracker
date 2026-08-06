package io.github.Mishaa105.price_tracker.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "offers")
@Getter
@Setter
@NoArgsConstructor
public class Offer
{
    @Id
    private Long id;

    @Column
    private String offerName;

    @Column
    private String startDate;

    @Column
    private String endDate; //offerAvailability

    @OneToMany(mappedBy = "offer")
    private List<CurrentPrice> currentPrices;

    @OneToMany(mappedBy = "offer")
    private List<Price> allPrices;
}
