package com.ecommerce.store.controllers;

import com.ecommerce.store.dtos.AddItemToCartRequest;
import com.ecommerce.store.dtos.CartDto;
import com.ecommerce.store.dtos.CartItemDto;
import com.ecommerce.store.dtos.UpdateCartItemRequest;
import com.ecommerce.store.exceptions.CartNotFoundException;
import com.ecommerce.store.exceptions.ProductNotFoundException;
import com.ecommerce.store.services.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    private CartService cartService;
    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder)
    {
        var cartDto= cartService.createCart();
        var uri= uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    public  ResponseEntity<CartItemDto> addToCart(@PathVariable UUID cartId,
                                                  @RequestBody AddItemToCartRequest request)
    {
        var cartItemDto= cartService.addToCart(cartId,request.getProductId());

        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    public CartDto getCart(@PathVariable UUID cartId)
    {
        var cartDto= cartService.getCart(cartId);
        return cartDto;
//        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public CartItemDto updateItem(@PathVariable("cartId") UUID cartId,
                                                  @PathVariable("productId") Long productId,
                                              @Valid @RequestBody UpdateCartItemRequest request)
    {
        var cartItemDto= cartService.updateItem(cartId,productId, request.getQuantity());
        return cartItemDto;
    }
    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<Void> removeItem(@PathVariable("cartId") UUID cartId,
                                           @PathVariable("productId") Long productId)
    {
        cartService.removeItem(cartId,productId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart(@PathVariable("cartId") UUID cartId)
    {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleCartNotFound()
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error","Cart not found."));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleProductNotFound()
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Error","Product not found in the cart."));
    }

}
