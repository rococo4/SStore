package com.example.cartservice.Factories;

import com.example.cartservice.store.Entities.CartEntity;
import com.google.protobuf.util.Timestamps;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import com.sstore.cartservice.CartService;
import com.sstore.orchestratorservice.OrchestratorControllerGrpc;
import com.sstore.userservice.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.google.protobuf.Timestamp;



@Component
@RequiredArgsConstructor
public class CartFactory {
    @Value("${OrchestratorService.grpc.server.port}")
    private int orchestratorPort;
    private final CartPositionFactory cartPositionFactory;
    public CartService.CartResponse makeCartResponse(CartEntity cartEntity) {
        return CartService.CartResponse.newBuilder()
                .setCartId(cartEntity.getId())
                .addAllCartPositions(cartEntity.getCartPositions().
                        stream()
                        .map(cartPositionFactory::makeCartPositionResponse)
                        .toList())
                .setCreatedAt(Timestamps.fromMillis(cartEntity.getCreatedAt().toEpochMilli()))
                .setUser(getUserById(cartEntity.getUserId()))
                .build();
    }
    private UserService.UserResponse getUserById(Long userId) {
        try {
            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", orchestratorPort)
                    .usePlaintext().build();
            OrchestratorControllerGrpc.OrchestratorControllerBlockingStub blockingStub =
                    OrchestratorControllerGrpc.newBlockingStub(channel);
            UserService.UserId userIdClass = UserService.UserId.newBuilder().setUserId(userId).build();
            return blockingStub.getUserById(userIdClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
