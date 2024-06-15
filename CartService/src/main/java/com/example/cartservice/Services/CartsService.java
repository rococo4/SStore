package com.example.cartservice.Services;


import com.example.cartservice.Factories.CartFactory;
import com.example.cartservice.Factories.CartPositionFactory;
import com.example.cartservice.store.Entities.CartEntity;
import com.example.cartservice.store.Repositories.CartPositionRepository;
import com.example.cartservice.store.Repositories.CartRepository;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.example.cartservice.CartService;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartsService {

    private final CartRepository cartRepository;
    private final CartPositionRepository cartPositionRepository;

    private final CartFactory cartFactory;
    private final CartPositionFactory cartPositionFactory;

    public void createCart(CartService.CartRequest request,
                           StreamObserver<CartService.CartResponse> responseObserver) {
        try {
            CartEntity cartEntity = CartEntity.builder()
                    .cartPositions(
                            request.getCartPositionsList().stream()
                                    .map(cartPositionRepository::findById)
                                    .map(Optional::orElseThrow)
                                    .toList()
                    )
                    .createdAt(Instant.ofEpochSecond(request.getCreatedAt().getSeconds()))
                    .userId(request.getUserId())
                    .build();
            responseObserver.onNext(cartFactory.
                    makeCartResponse(cartRepository.saveAndFlush(cartEntity)));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }

    }


    public void updateCart(CartService.CartRequestWithId request,
                           StreamObserver<CartService.CartResponse> responseObserver) {

        // todo: 404
        try {
            CartEntity cart = cartRepository.findById(request.getCartId()).orElseThrow();

            if (!request.getCart().getCartPositionsList().isEmpty()) {
                cart.setCartPositions(request.getCart().getCartPositionsList().stream()
                        .map(cartPositionRepository::findById)
                        .map(Optional::orElseThrow)
                        .toList());
            }
            if (request.getCart().getCreatedAt().isInitialized()) {
                cart.setCreatedAt(Instant.ofEpochSecond(request.getCart().getCreatedAt().getSeconds()));
            }
            if (request.getCart().getUserId() != 0) {
                cart.setUserId(request.getCart().getUserId());
            }
            responseObserver.onNext(cartFactory.makeCartResponse(cartRepository.saveAndFlush(cart)));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }


    public void getCart(CartService.CartId request,
                        StreamObserver<CartService.CartResponse> responseObserver) {
        try {
            responseObserver.onNext(
                    cartFactory.makeCartResponse(
                            cartRepository.findById(request.getCartId())
                                    .orElseThrow()));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }

    }


    public void deleteCart(CartService.CartId request,
                           StreamObserver<com.google.protobuf.Empty> responseObserver) {
        try {
            if (!cartRepository.existsById(request.getCartId())) {
                // todo: exception
            }
            cartRepository.deleteById(request.getCartId());
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }

    }


    public void updateListOfPositions(CartService.ListOfPositionsWithCartId request,
                                      StreamObserver<CartService.CartResponse> responseObserver) {
        try {
            // todo: 404
            CartEntity cart = cartRepository.findById(request.getCartId()).orElseThrow();


            cart.setCartPositions(request.getCartPositionsList().stream()
                    .map(CartService.CartPositionId::getCartPositionId)
                    .map(cartPositionRepository::findById)
                    .map(Optional::orElseThrow)
                    .toList());
            responseObserver.onNext(cartFactory.makeCartResponse(cartRepository.saveAndFlush(cart)));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }


    }
}
