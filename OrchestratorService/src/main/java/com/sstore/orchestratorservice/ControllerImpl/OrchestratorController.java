package com.sstore.orchestratorservice.ControllerImpl;

import com.sstore.orchestratorservice.Services.OrchestratorsService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import com.sstore.cartservice.CartService;
import com.sstore.orchestratorservice.OrchestratorControllerGrpc;
import com.sstore.orchestratorservice.OrchestratorService;
import com.sstore.productservice.ProductService;
import com.sstore.userservice.UserService;

@RequiredArgsConstructor
public class OrchestratorController extends OrchestratorControllerGrpc.OrchestratorControllerImplBase {
    private final OrchestratorsService orchestratorService;

    @Override
    public void getUserById(UserService.UserId request,
                            StreamObserver<UserService.UserResponse> responseObserver) {
        orchestratorService.getUserById(request, responseObserver);
    }
    @Override
    public void getSneakerById(ProductService.SneakerId request,
                               StreamObserver<ProductService.SneakerResponse> responseObserver) {
        orchestratorService.getSneakerById(request, responseObserver);
    }
    @Override
    public void getCartById(CartService.CartId request,
                            StreamObserver<CartService.CartResponse> responseObserver) {
        orchestratorService.getCartById(request, responseObserver);
    }
}
