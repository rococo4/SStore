package com.sstore.orderservice.ControllerImpl;

import io.grpc.stub.StreamObserver;
import org.example.orderservice.OrderControllerGrpc;
import org.example.orderservice.OrderService;


public class OrderController extends OrderControllerGrpc.OrderControllerImplBase {
    @Override
    public void updateOrder(OrderService.OrderRequestWithId request,
                            StreamObserver<OrderService.OrderResponse> responseObserver) {

    }
    @Override
    public void createOrder(OrderService.OrderRequest request,
                            StreamObserver<OrderService.OrderResponse> responseObserver) {

    }
    @Override
    public void getOrder(OrderService.OrderId request,
                         StreamObserver<OrderService.OrderResponse> responseObserver) {

    }
    @Override
    public void deleteOrder(OrderService.OrderId request,
                            StreamObserver<com.google.protobuf.Empty> responseObserver) {

    }
    @Override
    public void changeOrderState(OrderService.StateOrderWithOrderId request,
                                 StreamObserver<OrderService.OrderResponse> responseObserver) {

    }
}
