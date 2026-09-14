package io.github.Mishaa105.price_tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "wishlist")
@Getter
@Setter
@NoArgsConstructor
public class Wishlist
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime savingTime;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users userId;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product productId;
}
