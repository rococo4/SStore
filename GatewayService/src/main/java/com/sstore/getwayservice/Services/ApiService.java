package com.sstore.getwayservice.Services;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.cartservice.CartControllerGrpc;
import org.example.cartservice.CartPositionControllerGrpc;
import org.example.cartservice.CartService;
import org.example.orderservice.OrderControllerGrpc;
import org.example.orderservice.OrderService;
import org.example.productservice.CategoryControllerGrpc;
import org.example.productservice.ProductService;
import org.example.productservice.SneakerControllerGrpc;
import org.example.userservice.UserControllerGrpc;
import org.example.userservice.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiService {

    @Value("${UserService.grpc.server.port}")
    private int userServicePort;
    @Value("${CartService.grpc.server.port}")
    private int cartServicePort;
    @Value("${ProductService.grpc.server.port}")
    private int productServicePort;

    @Value("${OrderService.grpc.server.port}")
    private int orderServicePort;

    private final VerifyService verifyService;

    public Object sendRequest(String token,
                              String serviceName,
                              String requestType,
                              String endpoint, Object body) {

        UserService.VerifyResponse verifyResponse = verifyService.verify(token);

        ManagedChannel orderChannel = ManagedChannelBuilder.forAddress("localhost", orderServicePort)
                .usePlaintext().build();
        OrderControllerGrpc.OrderControllerBlockingStub orderStub = OrderControllerGrpc.newBlockingStub(orderChannel);

        ManagedChannel cartChannel = ManagedChannelBuilder.forAddress("localhost", cartServicePort)
                .usePlaintext().build();
        CartControllerGrpc.CartControllerBlockingStub cartStub = CartControllerGrpc.newBlockingStub(cartChannel);
        CartPositionControllerGrpc.CartPositionControllerBlockingStub cartPositionStub = CartPositionControllerGrpc
                .newBlockingStub(cartChannel);

        ManagedChannel productChannel = ManagedChannelBuilder.forAddress("localhost", productServicePort)
                .usePlaintext().build();
        SneakerControllerGrpc.SneakerControllerBlockingStub productStub = SneakerControllerGrpc
                .newBlockingStub(productChannel);
        CategoryControllerGrpc.CategoryControllerBlockingStub categoryStub = CategoryControllerGrpc
                .newBlockingStub(productChannel);


        try {
            switch (serviceName) {
                case "ProductService": {
                    orderChannel.shutdown();
                    cartChannel.shutdown();
                    switch (endpoint) {
                        case "rud_category":
                            switch (requestType) {
                                case "PUT":
                                    if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                        throw new IllegalArgumentException("Invalid role");
                                    }
                                    assert body instanceof ProductService.CategoryIdName : "Invalid body";
                                    return categoryStub.updateCategory((ProductService.CategoryIdName) body);

                                case "GET":
                                    assert body instanceof ProductService.CategoryId : "Invalid body";
                                    return categoryStub.getCategory((ProductService.CategoryId) body);
                                case "DELETE":
                                    if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                        throw new IllegalArgumentException("Invalid role");
                                    }
                                    assert body instanceof ProductService.CategoryId : "Invalid body";
                                    return categoryStub.deleteCategory((ProductService.CategoryId) body);
                                default:
                                    throw new IllegalArgumentException("Invalid request type: " + requestType);
                            }
                        case "create_category": {
                            if (requestType.equals("POST")) {
                                if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                    throw new IllegalArgumentException("Invalid role");
                                }
                                assert body instanceof ProductService.CategoryName : "Invalid body";
                                return categoryStub.addCategory((ProductService.CategoryName) body);

                            } else {
                                throw new IllegalArgumentException("Invalid request type: " + requestType);
                            }
                        }
                        case "getAllCategory": {
                            if (requestType.equals("GET")) {
                                assert body instanceof com.google.protobuf.Empty : "Invalid body";
                                return categoryStub.getAllCategory((com.google.protobuf.Empty) body);
                            } else {
                                throw new IllegalArgumentException("Invalid request type: " + requestType);
                            }
                        }
                        case "rud_sneaker": {
                            switch (requestType) {
                                case "PUT":
                                    if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                        throw new IllegalArgumentException("Invalid role");
                                    }
                                    assert body instanceof ProductService.SneakerRequestWithId : "Invalid body";
                                    return productStub.updateSneaker((ProductService.SneakerRequestWithId) body);
                                case "GET":
                                    assert body instanceof ProductService.SneakerId : "Invalid body";
                                    return productStub.getSneaker((ProductService.SneakerId) body);
                                case "DELETE":
                                    if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                        throw new IllegalArgumentException("Invalid role");
                                    }
                                    assert body instanceof ProductService.SneakerId : "Invalid body";
                                    return productStub.deleteSneaker((ProductService.SneakerId) body);
                                default:
                                    throw new IllegalArgumentException("Invalid request type: " + requestType);
                            }
                        }
                        case "create_sneaker": {
                            if (requestType.equals("POST")) {
                                if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                    throw new IllegalArgumentException("Invalid role");
                                }
                                assert body instanceof ProductService.SneakerRequest : "Invalid body";
                                return productStub.addSneaker((ProductService.SneakerRequest) body);
                            } else {
                                throw new IllegalArgumentException("Invalid request type: " + requestType);
                            }
                        }
                        case "get_all_sneakers_filter": {
                            if (requestType.equals("GET")) {
                                assert body instanceof ProductService.SneakerId : "Invalid body";
                                return productStub.getSneaker((ProductService.SneakerId) body);
                            } else {
                                throw new IllegalArgumentException("Invalid request type: " + requestType);
                            }
                        }
                        case "changeQuantity": {
                            if (requestType.equals("PUT")) {
                                if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                    throw new IllegalArgumentException("Invalid role");
                                }
                                assert body instanceof ProductService.SneakerIdQuantity : "Invalid body";
                                return productStub.changeQuantity((ProductService.SneakerIdQuantity) body);

                            } else {
                                throw new IllegalArgumentException("Invalid request type: " + requestType);
                            }
                        }
                        default:
                            throw new IllegalArgumentException("Invalid endpoint: " + endpoint);
                    }
                }
                case "OrderService": {
                    productChannel.shutdown();
                    cartChannel.shutdown();
                    switch (endpoint) {
                        case "update_order": {
                            if (requestType.equals("PUT")) {
                                if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                    throw new IllegalArgumentException("Invalid role");
                                }
                                assert body instanceof OrderService.OrderRequest : "Invalid body";
                                return orderStub.createOrder((OrderService.OrderRequest) body);
                            } else {
                                throw new IllegalArgumentException("Invalid request type: " + requestType);
                            }
                        }
                        case "rud_order": {
                            switch (requestType) {
                                case "PUT":
                                    if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                        throw new IllegalArgumentException("Invalid role");
                                    }
                                    assert body instanceof OrderService.OrderRequestWithId : "Invalid body";
                                    return orderStub.updateOrder((OrderService.OrderRequestWithId) body);
                                case "GET":
                                    assert body instanceof OrderService.OrderId : "Invalid body";
                                    return orderStub.getOrder((OrderService.OrderId) body);
                                case "DELETE":
                                    if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                        throw new IllegalArgumentException("Invalid role");
                                    }
                                    assert body instanceof OrderService.OrderId : "Invalid body";
                                    return orderStub.deleteOrder((OrderService.OrderId) body);
                                default:
                                    throw new IllegalArgumentException("Invalid request type: " + requestType);
                            }
                        }
                        case "changeOrderState": {
                            if (requestType.equals("PUT")) {
                                if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                    throw new IllegalArgumentException("Invalid role");
                                }
                                assert body instanceof OrderService.StateOrderWithOrderId : "Invalid body";
                                return orderStub.changeOrderState((OrderService.StateOrderWithOrderId) body);

                            } else {
                                throw new IllegalArgumentException("Invalid request type: " + requestType);
                            }
                        }
                        default:
                            throw new IllegalArgumentException("Invalid endpoint: " + endpoint);
                    }
                }
                case "CartService": {
                    productChannel.shutdown();
                    orderChannel.shutdown();
                    switch (endpoint) {
                        case "rud_cart": {
                            switch (requestType) {
                                case "PUT":
                                    if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                        throw new IllegalArgumentException("Invalid role");
                                    }
                                    assert body instanceof CartService.CartRequestWithId : "Invalid body";
                                    return cartStub.updateCart((CartService.CartRequestWithId) body);
                                case "GET":
                                    assert body instanceof CartService.CartId : "Invalid body";
                                    return cartStub.getCart((CartService.CartId) body);
                                case "DELETE":
                                    if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                        throw new IllegalArgumentException("Invalid role");
                                    }
                                    assert body instanceof CartService.CartId : "Invalid body";
                                    return cartStub.deleteCart((CartService.CartId) body);
                                default:
                                    throw new IllegalArgumentException("Invalid request type: " + requestType);
                            }
                        }
                        case "create_cart": {
                            if (requestType.equals("POST")) {
                                assert body instanceof CartService.CartRequest : "Invalid body";
                                return cartStub.createCart((CartService.CartRequest) body);
                            } else {
                                throw new IllegalArgumentException("Invalid request type: " + requestType);
                            }
                        }
                        case "updateListOfPositions": {
                            if (requestType.equals("PUT")) {
                                assert body instanceof CartService.ListOfPositionsWithCartId : "Invalid body";
                                return cartStub.updateListOfPositions((CartService.ListOfPositionsWithCartId) body);
                            } else {
                                throw new IllegalArgumentException("Invalid request type: " + requestType);
                            }
                        }
                        case "rud_position": {
                            switch (requestType) {
                                case "PUT":
                                    if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                        throw new IllegalArgumentException("Invalid role");
                                    }
                                    assert body instanceof CartService.CartPositionRequestWithId : "Invalid body";
                                    return cartPositionStub
                                            .updateCartPosition((CartService.CartPositionRequestWithId) body);
                                case "GET":
                                    assert body instanceof CartService.CartPositionId : "Invalid body";
                                    return cartPositionStub
                                            .getCartPosition((CartService.CartPositionId) body);
                                case "DELETE":
                                    if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                        throw new IllegalArgumentException("Invalid role");
                                    }
                                    assert body instanceof CartService.CartPositionId : "Invalid body";
                                    return cartPositionStub
                                            .deleteCartPosition((CartService.CartPositionId) body);
                                default:
                                    throw new IllegalArgumentException("Invalid request type: " + requestType);
                            }
                        }
                        case "create_position": {
                            if (requestType.equals("POST")) {
                                if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                    throw new IllegalArgumentException("Invalid role");
                                }
                                assert body instanceof CartService.CartPositionRequest : "Invalid body";
                                return cartPositionStub
                                        .createCartPosition((CartService.CartPositionRequest) body);
                            } else {
                                throw new IllegalArgumentException("Invalid request type: " + requestType);
                            }
                        }
                        default:
                            throw new IllegalArgumentException("Invalid endpoint: " + endpoint);
                    }
                }
                default:
                    throw new IllegalArgumentException("Invalid service: " + serviceName);
            }
        } finally {
            productChannel.shutdown();
            orderChannel.shutdown();
            cartChannel.shutdown();
        }

    }
}
