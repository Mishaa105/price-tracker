package io.github.Mishaa105.price_tracker.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Products
{
    @Id
    private int id;

    @Column
    private String name;

    @Column
    private String previewUrl;
}
