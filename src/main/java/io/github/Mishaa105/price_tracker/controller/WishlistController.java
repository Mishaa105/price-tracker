package io.github.Mishaa105.price_tracker.controller;

import io.github.Mishaa105.price_tracker.service.wishlist.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/wishlist")
public class WishlistController
{
    private final WishlistService wishlistService;

    @PostMapping("/api/v1/wishlist")
    void addProductToWishlistByUserid(@RequestParam String productId)
    {
        UUID currentUserId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        wishlistService.addProductToWishlist(productId, currentUserId);
    }
}
