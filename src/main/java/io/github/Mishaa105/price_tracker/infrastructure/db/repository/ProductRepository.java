package io.github.Mishaa105.price_tracker.infrastructure.db.repository;

import io.github.Mishaa105.price_tracker.dto.product_page.CurrentPricePoint;
import io.github.Mishaa105.price_tracker.dto.product_page.PricePoint;
import io.github.Mishaa105.price_tracker.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>
{
    @Query("SELECT p.productId FROM Product p WHERE p.name ILIKE CONCAT('%', :query, '%') LIMIT 48")
    List<String> getProductsIdByName(@Param("query") String query);

    //TODO Рефакторинг

    @Query(value = "SELECT p.name FROM products p WHERE product_id = :productId ORDER BY p.saving_time DESC ", nativeQuery = true)
    String getProductNameById(@Param("productId") String productId);

    @Query(value = "SELECT p.description FROM products p WHERE product_id = :productId ORDER BY p.saving_time DESC ", nativeQuery = true)
    String getProductDescriptionById(@Param("productId") String productId);

    @Query(value = "SELECT p.preview_url FROM products p WHERE product_id = :productId ORDER BY p.saving_time DESC ", nativeQuery = true)
    String getProductPreviewById(@Param("productId") String productId);

    @Query(value = "SELECT p.edition FROM products p WHERE product_id = :productId ORDER BY p.saving_time DESC ", nativeQuery = true)
    String getProductEditionById(@Param("productId") String productId);

    @Query(value = "SELECT p.average_rating FROM products p WHERE product_id = :productId ORDER BY p.saving_time DESC ", nativeQuery = true)
    Double getProductAverageRatingById(@Param("productId") String productId);

    @Query(value = "SELECT p.ratings_count FROM products p WHERE product_id = :productId ORDER BY p.saving_time DESC ", nativeQuery = true)
    Integer getProductRatingsCountById(@Param("productId") String productId);

    @Query(value = "SELECT p.release_date FROM products p WHERE product_id = :productId ORDER BY p.saving_time DESC ", nativeQuery = true)
    String getProductReleaseDateById(@Param("productId") String productId);

    @Query(value = "SELECT s.type FROM store_classifications s JOIN public.products p on s.id = p.store_classification_id " +
            "WHERE product_id = :productId ORDER BY p.saving_time DESC", nativeQuery = true)
    String getProductStoreClassificationById(@Param("productId") String productId);

    @Query(value = "SELECT pub.name FROM publishers pub JOIN public.products p on pub.id = p.publisher_name_id " +
            "WHERE product_id = :productId ORDER BY p.saving_time DESC", nativeQuery = true)
    String getProductPublisherNameById(@Param("productId") String productId);

    @Query(value = "SELECT pl.platform FROM platforms pl JOIN public.product_platforms pp on pl.id = pp.platform_id " +
            "JOIN public.products p on p.product_id = pp.product_id WHERE p.product_id = :productId ORDER BY p.saving_time DESC", nativeQuery = true)
    List<String> getProductPlatformsById(@Param("productId") String productId);

    @Query(value = "SELECT g.genre FROM genres g JOIN public.product_genres pg on g.id = pg.genre_id " +
            "JOIN public.products p on p.product_id = pg.product_id WHERE p.product_id = :productId ORDER BY p.saving_time DESC", nativeQuery = true)
    List<String> getProductGenresById(@Param("productId") String productId);

    @Query(value = "SELECT l.language FROM languages l JOIN public.product_languages pl on l.id = pl.language_id " +
            "JOIN products p on p.product_id = pl.product_id WHERE l.type = 'SCREEN' AND p.product_id = :productId ORDER BY p.saving_time DESC", nativeQuery = true)
    List<String> getProductScreenLanguagesById(@Param("productId") String productId);

    @Query(value = "SELECT l.language FROM languages l JOIN public.product_languages pl on l.id = pl.language_id " +
            "JOIN products p on p.product_id = pl.product_id WHERE l.type = 'SPOKEN' AND p.product_id = :productId ORDER BY p.saving_time DESC", nativeQuery = true)
    List<String> getProductSpokenLanguagesById(@Param("productId") String productId);

    @Query("SELECT new io.github.Mishaa105.price_tracker.dto.product_page.PricePoint(ap.savingTime, " +
            "ap.discountPrice, ap.originalPrice, b.brand, c.currency, o.endDate, o.startDate) FROM Product p " +
            "LEFT JOIN p.allPrices ap LEFT JOIN ap.offerBrand b LEFT JOIN ap.priceCurrencyCode c LEFT JOIN ap.offer o WHERE p.productId = :productId ORDER BY ap.savingTime DESC")
    List<PricePoint> getProductAllPrices(@Param("productId") String productId);

    @Query("SELECT new io.github.Mishaa105.price_tracker.dto.product_page.CurrentPricePoint(cp.discountPrice, " +
            "cp.originalPrice, b.brand, c.currency, o.endDate, o.startDate) FROM Product p " +
            "LEFT JOIN p.currentPrices cp LEFT JOIN cp.offerBrand b LEFT JOIN cp.priceCurrencyCode c LEFT JOIN cp.offer o WHERE p.productId = :productId")
    List<CurrentPricePoint> getProductCurrentPrices(@Param("productId") String productId);
}
