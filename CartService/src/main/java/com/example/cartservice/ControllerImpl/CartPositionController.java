package com.example.cartservice.ControllerImpl;

import com.example.cartservice.Services.CartPositionsService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.example.cartservice.CartPositionControllerGrpc;
import org.example.cartservice.CartService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequiredArgsConstructor
public class CartPositionController extends CartPositionControllerGrpc.CartPositionControllerImplBase {
    private final CartPositionsService cartPositionsService;
    @Override
    public void createCartPosition(CartService.CartPositionRequest request,
                                   StreamObserver<CartService.CartPositionResponse> responseObserver) {
        cartPositionsService.createCartPosition(request, responseObserver);
    }


    @Override
    public void updateCartPosition(CartService.CartPositionRequestWithId request,
                                   StreamObserver<CartService.CartPositionResponse> responseObserver) {
        cartPositionsService.updateCartPosition(request, responseObserver);
    }


    @Override
    public void getCartPosition(CartService.CartPositionId request,
                                StreamObserver<CartService.CartPositionResponse> responseObserver) {
        cartPositionsService.getCartPosition(request, responseObserver);
    }

    @Override
    public void deleteCartPosition(CartService.CartPositionId request,
                                   StreamObserver<com.google.protobuf.Empty> responseObserver) {
        cartPositionsService.deleteCartPosition(request, responseObserver);
    }

}
