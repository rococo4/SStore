package com.sstore.orderservice.Services;

import com.sstore.orderservice.Factories.OrderFactory;
import com.sstore.orderservice.Store.Entities.OrderEntity;
import com.sstore.orderservice.Store.Repositories.OrderRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.OrderService;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrderRepository orderRepository;
    private final OrderFactory orderFactory;

    public void updateOrder(OrderService.OrderRequestWithId request,
                            StreamObserver<OrderService.OrderResponse> responseObserver) {

        try {
            OrderEntity orderEntity = orderRepository.findById(request.getOrderId()).orElseThrow();
            if (request.getOrderRequest().getCartId() != 0) {
                orderEntity.setCartId(request.getOrderRequest().getCartId());
            }
            orderEntity.setStateOfOrder(request.getOrderRequest().getState());
            if (request.getOrderRequest().getUserId() != 0) {
                orderEntity.setUserId(request.getOrderRequest().getUserId());
            }
            if (request.getOrderRequest().getCreatedAt().isInitialized()) {
                orderEntity.setCreatedAt(Instant.ofEpochSecond(request.getOrderRequest().getCreatedAt().getSeconds()));
            }
            responseObserver.onNext(orderFactory.makeOrderResponse(orderRepository.saveAndFlush(orderEntity)));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }


    }

    public void createOrder(OrderService.OrderRequest request,
                            StreamObserver<OrderService.OrderResponse> responseObserver) {
        try {
            responseObserver.onNext(
                    orderFactory.makeOrderResponse(OrderEntity.builder()
                            .stateOfOrder(request.getState())
                            .createdAt(Instant.ofEpochSecond(request.getCreatedAt().getSeconds()))
                            .cartId(request.getCartId())
                            .userId(request.getUserId())
                            .build()));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    public void getOrder(OrderService.OrderId request,
                         StreamObserver<OrderService.OrderResponse> responseObserver) {
        try {
            responseObserver.onNext(
                    orderFactory.makeOrderResponse(
                            orderRepository.findById(request.getOrderId()).orElseThrow()));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    public void deleteOrder(OrderService.OrderId request,
                            StreamObserver<com.google.protobuf.Empty> responseObserver) {
        try {
            if (!orderRepository.existsById(request.getOrderId())) {
                throw new RuntimeException();
            }
            orderRepository.deleteById(request.getOrderId());
            responseObserver.onNext(com.google.protobuf.Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (RuntimeException e) {
            responseObserver.onError(e);
        }

    }

    public void changeOrderState(OrderService.StateOrderWithOrderId request,
                                 StreamObserver<OrderService.OrderResponse> responseObserver) {
        try {
            OrderEntity orderEntity = orderRepository.findById(request.getOrderId()).orElseThrow();

            orderEntity.setStateOfOrder(request.getState());
            responseObserver.onNext(
                    orderFactory.makeOrderResponse(orderRepository.saveAndFlush(orderEntity))
            );
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }

    }
}
