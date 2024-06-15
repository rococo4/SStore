package com.example.cartservice.ControllerImpl;

import io.grpc.stub.StreamObserver;
import org.example.cartservice.CartControllerGrpc;
import org.example.cartservice.CartService;

public class CartController extends CartControllerGrpc.CartControllerImplBase {
    @Override
    public void createCart(CartService.CartRequest request,
                           StreamObserver<CartService.CartResponse> responseObserver) {

    }


    @Override
    public void updateCart(CartService.CartRequestWithId request,
                           StreamObserver<CartService.CartResponse> responseObserver) {

    }


    @Override
    public void getCart(CartService.CartId request,
                        StreamObserver<CartService.CartResponse> responseObserver) {

    }


    @Override
    public void deleteCart(CartService.CartId request,
                           StreamObserver<com.google.protobuf.Empty> responseObserver) {

    }

    @Override
    public void updateListOfPositions(CartService.ListOfPositionsWithCartId request,
                                      StreamObserver<CartService.CartResponse> responseObserver) {

    }

}
