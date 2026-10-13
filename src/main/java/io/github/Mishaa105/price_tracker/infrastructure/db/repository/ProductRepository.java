package io.github.Mishaa105.price_tracker.infrastructure.db.repository;

import io.github.Mishaa105.price_tracker.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>
{
    @Query("SELECT p.productId FROM Product p WHERE name LIKE CONCAT('%', :query, '%') LIMIT 48")
    List<String> getProductsIdByName(@Param("query") String query);
}
