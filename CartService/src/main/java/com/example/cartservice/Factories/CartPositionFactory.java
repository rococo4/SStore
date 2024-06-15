package com.example.cartservice.Factories;

import com.example.cartservice.store.Entities.CartPositionEntity;
import jakarta.persistence.Column;
import org.example.cartservice.CartService;
import org.example.productservice.ProductService;
import org.springframework.stereotype.Component;

@Component
public class CartPositionFactory {
    public CartService.CartPositionResponse makeCartPositionResponse(CartPositionEntity cartPositionEntity) {
        return CartService.CartPositionResponse.newBuilder()
                .setPositionId(cartPositionEntity.getId())
                .setSneaker(getSneakerById(cartPositionEntity.getSneakerId()))
                .setQuantity(cartPositionEntity.getQuantity())
                .build();
    }
    private ProductService.SneakerResponse getSneakerById(Long sneakerId) {
        return null;
    }
}
