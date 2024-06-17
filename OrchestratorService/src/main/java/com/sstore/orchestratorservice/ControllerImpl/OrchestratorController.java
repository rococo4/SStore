package com.sstore.orchestratorservice.ControllerImpl;

import com.sstore.orchestratorservice.Services.OrchestratorsService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.example.cartservice.CartService;
import org.example.orchestratorservice.OrchestratorControllerGrpc;
import org.example.orchestratorservice.OrchestratorService;
import org.example.productservice.ProductService;
@RequiredArgsConstructor
public class OrchestratorController extends OrchestratorControllerGrpc.OrchestratorControllerImplBase {
    private final OrchestratorsService orchestratorService;

    @Override
    public void getUserById(OrchestratorService.UserId request,
                            StreamObserver<CartService.UserResponse> responseObserver) {
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
