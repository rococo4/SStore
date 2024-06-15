package com.example.cartservice.Factories;

import com.example.cartservice.store.Entities.CartEntity;
import com.google.protobuf.util.Timestamps;
import lombok.RequiredArgsConstructor;
import org.example.cartservice.CartService;
import org.springframework.stereotype.Component;
import com.google.protobuf.Timestamp;



@Component
@RequiredArgsConstructor
public class CartFactory {
    private final CartPositionFactory cartPositionFactory;
    public CartService.CartResponse makeCartResponse(CartEntity cartEntity) {
        return CartService.CartResponse.newBuilder()
                .setCartId(cartEntity.getId())
                .addAllCartPositions(cartEntity.getCartPositions().
                        stream()
                        .map(cartPositionFactory::makeCartPositionResponse)
                        .toList())
                .setCreatedAt(Timestamps.fromMillis(cartEntity.getCreatedAt().toEpochMilli()))
                .setUser(getUserById(cartEntity.getUserId()))
                .build();
    }
    private CartService.UserResponse getUserById(Long userId) {
        return null;
    }

}
