package com.sstore.orderservice.ControllerImpl;

import com.sstore.orderservice.Services.OrdersService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import com.sstore.orderservice.OrderControllerGrpc;
import com.sstore.orderservice.OrderService;

@RequiredArgsConstructor
public class OrderController extends OrderControllerGrpc.OrderControllerImplBase {
    private final OrdersService ordersService;
    @Override
    public void updateOrder(OrderService.OrderRequestWithId request,
                            StreamObserver<OrderService.OrderResponse> responseObserver) {
        ordersService.updateOrder(request, responseObserver);
    }
    @Override
    public void createOrder(OrderService.OrderRequest request,
                            StreamObserver<OrderService.OrderResponse> responseObserver) {
        ordersService.createOrder(request, responseObserver);
    }
    @Override
    public void getOrder(OrderService.OrderId request,
                         StreamObserver<OrderService.OrderResponse> responseObserver) {
        ordersService.getOrder(request, responseObserver);
    }
    @Override
    public void deleteOrder(OrderService.OrderId request,
                            StreamObserver<com.google.protobuf.Empty> responseObserver) {
        ordersService.deleteOrder(request, responseObserver);
    }
    @Override
    public void changeOrderState(OrderService.StateOrderWithOrderId request,
                                 StreamObserver<OrderService.OrderResponse> responseObserver) {
        ordersService.changeOrderState(request, responseObserver);
    }
}
