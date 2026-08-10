package io.github.Mishaa105.price_tracker.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "platforms")
@Getter
@Setter
@NoArgsConstructor
public class Platform
{
    public Platform(String platform)
    {
        this.platform = platform;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String platform;

    @ManyToMany(mappedBy = "platforms")
    private Set<Product> products;
}
