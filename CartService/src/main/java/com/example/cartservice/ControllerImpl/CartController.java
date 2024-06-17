package com.example.cartservice.ControllerImpl;

import com.example.cartservice.Services.CartsService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.example.cartservice.CartControllerGrpc;
import org.example.cartservice.CartService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequiredArgsConstructor
public class CartController extends CartControllerGrpc.CartControllerImplBase {
    private final CartsService cartService;
    @Override
    public void createCart(CartService.CartRequest request,
                           StreamObserver<CartService.CartResponse> responseObserver) {
        cartService.createCart(request, responseObserver);
    }


    @Override
    public void updateCart(CartService.CartRequestWithId request,
                           StreamObserver<CartService.CartResponse> responseObserver) {
        cartService.updateCart(request, responseObserver);
    }


    @Override
    public void getCart(CartService.CartId request,
                        StreamObserver<CartService.CartResponse> responseObserver) {
        cartService.getCart(request, responseObserver);
    }


    @Override
    public void deleteCart(CartService.CartId request,
                           StreamObserver<com.google.protobuf.Empty> responseObserver) {
        cartService.deleteCart(request, responseObserver);
    }

    @Override
    public void updateListOfPositions(CartService.ListOfPositionsWithCartId request,
                                      StreamObserver<CartService.CartResponse> responseObserver) {
        cartService.updateListOfPositions(request, responseObserver);
    }

}
