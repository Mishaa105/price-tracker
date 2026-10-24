package io.github.Mishaa105.price_tracker.infrastructure.db.repository;

import io.github.Mishaa105.price_tracker.entity.Wishlist;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface WishlistRepository extends JpaRepository<Wishlist, Long>
{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Wishlist(product_id, user_id, saving_time) VALUES(:productId, :userId, CURRENT_TIMESTAMP ) " +
            "ON CONFLICT(product_id, user_id) DO NOTHING", nativeQuery = true)
    void addProductToWishlistByUserId(@Param("productId") String productId, @Param("userId") UUID userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM wishlist WHERE product_id = :productId AND user_id = :userId", nativeQuery = true)
    void deleteProductFromWishlistByUserId(@Param("productId") String productId, @Param("userId") UUID userId);

    @Query(value = "SELECT w.product_id FROM wishlist w WHERE w.user_id = :userId", nativeQuery = true)
    List<String> getAllProductsFromWishlistByUserId(@Param("userId") UUID userId);
}
