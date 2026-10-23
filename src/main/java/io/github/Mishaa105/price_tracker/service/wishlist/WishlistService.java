package io.github.Mishaa105.price_tracker.service.wishlist;

import io.github.Mishaa105.price_tracker.infrastructure.db.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishlistService
{
    private final WishlistRepository wishlistRepository;

    public void addProductToWishlist(String productId, UUID userID)
    {
        wishlistRepository.addProductToWishlistByUserId(productId, userID);
    }

    public void deleteProductFromWishlist(String productId, UUID userID)
    {
        wishlistRepository.deleteProductFromWishlistByUserId(productId, userID);
    }
}
