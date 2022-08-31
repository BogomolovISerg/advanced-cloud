package com.flamexander.cloud.service.Carts;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService service;

    @PostMapping
    public Cart getCurrentCart(@RequestBody String cartName){
        return service.getCurrentCart(cartName);
    }

    @PostMapping("/add/{id}")
    public void addProductToCart(@PathVariable Long id, @RequestBody String cartName){
        service.addProductByIdToCart(id, cartName);
    }

    @PostMapping("/clear")
    public void clearCart(@RequestBody String cartName){
        service.getCurrentCart(cartName).clear();
    }

    @PostMapping("/removeproduct{id}")
    public void removeProductFromCarts(@PathVariable Long id, @RequestBody String cartName){
        service.removeProductByIdToCart(id,cartName);
    }
}

