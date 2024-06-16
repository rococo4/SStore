package com.example.cartservice.Services;

import com.example.cartservice.Factories.CartPositionFactory;
import com.example.cartservice.store.Entities.CartPositionEntity;
import com.example.cartservice.store.Repositories.CartPositionRepository;
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
                                   StreamObserver<CartService.CartPositionResponse> responseObserver) {
        try {
            CartPositionEntity cart = CartPositionEntity.builder()
                    .quantity(request.getQuantity())
                    .sneakerId(request.getSneakerId())
                    .build();

            responseObserver.onNext(cartPositionFactory
                    .makeCartPositionResponse(cartPositionRepository.saveAndFlush(cart)));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }


    }


    public void updateCartPosition(CartService.CartPositionRequestWithId request,
                                   StreamObserver<CartService.CartPositionResponse> responseObserver) {


        try {
            // todo: throw exception
            CartPositionEntity cart = cartPositionRepository.findById(request.getCartPositionId()).orElseThrow();

            if (request.getCartPosition().getQuantity() != 0) {
                cart.setQuantity(request.getCartPosition().getQuantity());
            }
            if (request.getCartPosition().getSneakerId() != 0) {
                cart.setSneakerId(request.getCartPosition().getSneakerId());
            }

            responseObserver.onNext(cartPositionFactory
                    .makeCartPositionResponse(cartPositionRepository.saveAndFlush(cart)));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }

    }

    public void getCartPosition(CartService.CartPositionId request,
                                StreamObserver<CartService.CartPositionResponse> responseObserver) {
        try {
            responseObserver.onNext(
                    cartPositionFactory.makeCartPositionResponse
                            (cartPositionRepository
                                    .findById(request.getCartPositionId())
                                    .orElseThrow()));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }

    }

    public void deleteCartPosition(CartService.CartPositionId request,
                                   StreamObserver<com.google.protobuf.Empty> responseObserver) {
        try {
            cartPositionRepository.deleteById(request.getCartPositionId());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }

    }
}
