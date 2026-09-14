package io.github.Mishaa105.price_tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class Users
{
    public Users(String email, String password, String name)
    {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @OneToMany(mappedBy = "userId")
    private Set<Wishlist> wishlist;
}
