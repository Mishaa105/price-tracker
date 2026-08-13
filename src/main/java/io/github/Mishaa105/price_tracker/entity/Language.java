package io.github.Mishaa105.price_tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "languages")
@Getter
@Setter
@NoArgsConstructor
public class Language
{
    public Language(String language, String type)
    {
        this.language = language;
        this.type= type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String language;

    @Column
    private String type;

    @ManyToMany(mappedBy = "languages")
    private Set<Product> products;
}
