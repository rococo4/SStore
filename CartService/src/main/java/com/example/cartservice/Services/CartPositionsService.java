package com.example.cartservice.Services;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.example.cartservice.CartService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartPositionsService {
    private final CartPositionRepository cartPositionRepository;
    private final CartPositionFactory cartPositionFactory;

    public void createCartPosition(CartService.CartPositionRequest request,
                                     StreamObserver<CartService.CartPositionResponse> responseObserver) { {
        CartPositionEntity cart = CartPositionEntity.builder()
                .quantity(cartPositionRequest.getQuantity())
                .sneakerId(cartPositionRequest.getSneakerId())
                .build();
        return  cartPositionFactory.makeCartPositionResponse(cartPositionRepository.saveAndFlush(cart));

    }


    public CartPositionResponse updateCartPosition(
            Long cartPositionId,
            CartPositionRequest cartPositionRequest) {

        // todo: throw exception
        CartPositionEntity cart = cartPositionRepository.findById(cartPositionId).orElseThrow();

        if (cartPositionRequest.getQuantity() != null) {
            cart.setQuantity(cartPositionRequest.getQuantity());
        }
        if (cartPositionRequest.getSneakerId() != null) {
            cart.setSneakerId(cartPositionRequest.getSneakerId());
        }

        return cartPositionFactory.makeCartPositionResponse(cartPositionRepository.saveAndFlush(cart));
    }

    public CartPositionResponse getCartPosition(Long cartPositionId) {
        return cartPositionFactory.makeCartPositionResponse
                (cartPositionRepository
                        .findById(cartPositionId)
                        .orElseThrow());
    }

    public void deleteCartPosition(Long cartPositionId) {
        cartPositionRepository.deleteById(cartPositionId);
    }
}
