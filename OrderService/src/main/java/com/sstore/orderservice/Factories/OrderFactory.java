package com.sstore.orderservice.Factories;

import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import com.sstore.orderservice.Store.Entities.OrderEntity;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.cartservice.CartControllerGrpc;
import org.example.orchestratorservice.OrchestratorControllerGrpc;
import org.example.productservice.SneakerControllerGrpc;
import org.springframework.beans.factory.annotation.Value;
import org.example.cartservice.CartService;
import org.example.orderservice.OrderService;
import org.example.userservice.UserService;
import org.springframework.stereotype.Component;

@Component
public class OrderFactory {
    @Value("${OrchestratorService.grpc.server.port}")
    private int orchestratorPort;
    public OrderService.OrderResponse makeOrderResponse(OrderEntity orderEntity) {
        return OrderService.OrderResponse.newBuilder()
                .setCart(getCartById(orderEntity.getCartId()))
                .setOrderId(orderEntity.getId())
                .setCreatedAt(Timestamps.fromMillis(orderEntity.getCreatedAt().toEpochMilli()))
                .setUser(getUserById(orderEntity.getUserId()))
                .build();
    }
    private CartService.CartResponse getCartById(Long cartId) {
        try {
            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", orchestratorPort)
                    .usePlaintext().build();
            OrchestratorControllerGrpc.OrchestratorControllerBlockingStub blockingStub =
                    OrchestratorControllerGrpc.newBlockingStub(channel);
            CartService.CartId cartIdClass = CartService.CartId.newBuilder().setCartId(cartId).build();
            return blockingStub.getCartById(cartIdClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
