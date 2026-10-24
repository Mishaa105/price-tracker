package io.github.Mishaa105.price_tracker.service.wishlist;

import io.github.Mishaa105.price_tracker.infrastructure.db.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishlistService
{
    private final WishlistRepository wishlistRepository;

    public void addProductToWishlist(String productId, UUID userId)
    {
        wishlistRepository.addProductToWishlistByUserId(productId, userId);
    }

    public void deleteProductFromWishlist(String productId, UUID userId)
    {
        wishlistRepository.deleteProductFromWishlistByUserId(productId, userId);
    }

    public List<String> getAllProductsFromWishlist(UUID userId)
    {
        return wishlistRepository.getAllProductsFromWishlistByUserId(userId);
    }
}
