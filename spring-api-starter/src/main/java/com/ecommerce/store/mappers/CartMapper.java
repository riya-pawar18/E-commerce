package com.ecommerce.store.mappers;

import com.ecommerce.store.dtos.CartDto;
import com.ecommerce.store.dtos.CartItemDto;
import com.ecommerce.store.entities.Cart;
import com.ecommerce.store.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper
{

    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}
