package io.github.Mishaa105.price_tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "store_classifications")
@Getter
@Setter
@NoArgsConstructor
public class StoreClassification
{
    public StoreClassification(String type)
    {
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String type;

    @OneToMany(mappedBy = "storeClassification")
    private List<Product> products;
}
