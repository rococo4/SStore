package com.sstore.orchestratorservice.Services;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.example.cartservice.CartControllerGrpc;
import org.example.cartservice.CartService;
import org.example.orchestratorservice.OrchestratorService;
import org.example.productservice.ProductService;
import org.example.productservice.SneakerControllerGrpc;
import org.example.userservice.UserControllerGrpc;
import org.example.userservice.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrchestratorsService {

    @Value("${ProductService.grpc.server.port}")
    private int productServicePort;
    @Value("${CartService.grpc.server.port}")
    private int cartServicePort;
    @Value("${UserService.grpc.server.port}")
    private int userServicePort;

    public void getUserById(UserService.UserId request,
                            StreamObserver<UserService.UserResponse> responseObserver) {
        try {
            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", userServicePort)
                    .usePlaintext().build();
            UserControllerGrpc.UserControllerBlockingStub blockingStub =
                    UserControllerGrpc.newBlockingStub(channel);
            UserService.UserResponse userResponse = blockingStub.getUserById(request);
            responseObserver.onNext(userResponse);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    public void getSneakerById(ProductService.SneakerId request,
                               StreamObserver<ProductService.SneakerResponse> responseObserver) {
        try {
            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", productServicePort)
                    .usePlaintext().build();
            SneakerControllerGrpc.SneakerControllerBlockingStub blockingStub =
                    SneakerControllerGrpc.newBlockingStub(channel);
            ProductService.SneakerResponse sneakerResponse = blockingStub.getSneaker(request);
            responseObserver.onNext(sneakerResponse);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    public void getCartById(CartService.CartId request,
                            StreamObserver<CartService.CartResponse> responseObserver) {
        try {
            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", cartServicePort)
                    .usePlaintext().build();
            CartControllerGrpc.CartControllerBlockingStub blockingStub =
                    CartControllerGrpc.newBlockingStub(channel);
            CartService.CartResponse cartResponse = blockingStub.getCart(request);
            responseObserver.onNext(cartResponse);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
