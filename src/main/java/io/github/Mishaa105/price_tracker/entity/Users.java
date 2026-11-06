package io.github.Mishaa105.price_tracker.entity;

import io.github.Mishaa105.price_tracker.enums.auth.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(name = "uk_users_email", columnNames = {"email"})})
@Getter
@Setter
@NoArgsConstructor
public class Users
{
    public Users(String email, String password, String name, Role role)
    {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
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

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "userId")
    private Set<Wishlist> wishlist;
}
