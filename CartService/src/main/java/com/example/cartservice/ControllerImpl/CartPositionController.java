package com.example.cartservice.ControllerImpl;

import io.grpc.stub.StreamObserver;
import org.example.cartservice.CartPositionControllerGrpc;
import org.example.cartservice.CartService;

public class CartPositionController extends CartPositionControllerGrpc.CartPositionControllerImplBase {
    @Override
    public void createCartPosition(CartService.CartPositionRequest request,
                                   StreamObserver<CartService.CartPositionResponse> responseObserver) {

    }


    @Override
    public void updateCartPosition(CartService.CartRequestWithId request,
                                   StreamObserver<CartService.CartPositionResponse> responseObserver) {

    }


    @Override
    public void getCartPosition(CartService.CartPositionId request,
                                StreamObserver<CartService.CartPositionResponse> responseObserver) {

    }

    @Override
    public void deleteCartPosition(CartService.CartPositionId request,
                                   StreamObserver<CartService.CartPositionResponse> responseObserver) {

    }

}
