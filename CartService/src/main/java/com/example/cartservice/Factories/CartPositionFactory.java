package com.example.cartservice.Factories;

import com.example.cartservice.store.Entities.CartPositionEntity;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.example.cartservice.CartService;
import org.example.orchestratorservice.OrchestratorControllerGrpc;
import org.example.productservice.ProductService;
import org.springframework.stereotype.Component;

@Component
public class CartPositionFactory {
    @Value("${OrchestratorService.grpc.server.port}")
    private int orchestratorPort;
    public CartService.CartPositionResponse makeCartPositionResponse(CartPositionEntity cartPositionEntity) {
        return CartService.CartPositionResponse.newBuilder()
                .setPositionId(cartPositionEntity.getId())
                .setSneaker(getSneakerById(cartPositionEntity.getSneakerId()))
                .setQuantity(cartPositionEntity.getQuantity())
                .build();
    }
    private ProductService.SneakerResponse getSneakerById(Long sneakerId) {
        try {
            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", orchestratorPort)
                    .usePlaintext().build();
            OrchestratorControllerGrpc.OrchestratorControllerBlockingStub blockingStub =
                    OrchestratorControllerGrpc.newBlockingStub(channel);
            ProductService.SneakerId sneakerIdClass = ProductService.SneakerId.newBuilder()
                    .setSneakerId(sneakerId)
                    .build();
            return blockingStub.getSneakerById(sneakerIdClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
