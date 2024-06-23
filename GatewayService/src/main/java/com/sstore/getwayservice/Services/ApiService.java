package com.sstore.getwayservice.Services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.userservice.UserService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiService {
    private
    public Object sendRequest(String token,
                              String serviceName,
                              String requestType,
                              String endpoint, Object body) {
        UserService.VerifyResponse verifyResponse = verifyService.verify(token);
        switch (serviceName) {
            case "ProductService": {
                switch (endpoint) {
                    case "rud_category":
                        switch (requestType) {
                            case "PUT":
                                if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                    throw new IllegalArgumentException("Invalid role");
                                }
                                break;
                            case "GET":
                                break;
                            case "DELETE":
                                if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                    throw new IllegalArgumentException("Invalid role");
                                }
                                break;
                            default:
                                throw new IllegalArgumentException("Invalid request type: " + requestType);
                        }
                    case "create_category": {
                        if (requestType.equals("POST")) {
                            if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                throw new IllegalArgumentException("Invalid role");
                            }
                        } else {
                            throw new IllegalArgumentException("Invalid request type: " + requestType);
                        }
                        break;
                    }
                    case "getAllCategory": {
                        if (requestType.equals("GET")) {
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
                                break;
                            case "GET":
                                break;
                            case "DELETE":
                                if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                    throw new IllegalArgumentException("Invalid role");
                                }
                                break;
                            default:
                                throw new IllegalArgumentException("Invalid request type: " + requestType);
                        }
                        break;
                    }
                    case "create_sneaker": {
                        if (requestType.equals("POST")) {
                            if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                throw new IllegalArgumentException("Invalid role");
                            }
                        } else {
                            throw new IllegalArgumentException("Invalid request type: " + requestType);
                        }
                        break;
                    }
                    case "get_all_sneakers_filter": {
                        if (requestType.equals("GET")) {

                        } else {
                            throw new IllegalArgumentException("Invalid request type: " + requestType);
                        }
                        break;
                    }
                    case "changeQuantity": {
                        if (requestType.equals("PUT")) {
                            if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                throw new IllegalArgumentException("Invalid role");
                            }

                        } else {
                            throw new IllegalArgumentException("Invalid request type: " + requestType);
                        }
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Invalid endpoint: " + endpoint);
                }
            }
            case "OrderService": {
                switch (endpoint) {
                    case "update_order" : {
                        if (requestType.equals("PUT")) {
                            if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                throw new IllegalArgumentException("Invalid role");
                            }

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
                                break;
                            case "GET":
                                break;
                            case "DELETE":
                                if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                    throw new IllegalArgumentException("Invalid role");
                                }
                                break;
                            default:
                                throw new IllegalArgumentException("Invalid request type: " + requestType);
                        }
                    }
                    case "changeOrderState": {
                        if (requestType.equals("PUT")) {
                            if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                throw new IllegalArgumentException("Invalid role");
                            }

                        } else {
                            throw new IllegalArgumentException("Invalid request type: " + requestType);
                        }
                    }
                    default:
                        throw new IllegalArgumentException("Invalid endpoint: " + endpoint);
                }
            }
            case "CartService": {
                switch (endpoint) {
                    case "rud_cart": {
                        switch (requestType) {
                            case "PUT":
                                if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                    throw new IllegalArgumentException("Invalid role");
                                }
                                break;
                            case "GET":
                                break;
                            case "DELETE":
                                if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                    throw new IllegalArgumentException("Invalid role");
                                }
                                break;
                            default:
                                throw new IllegalArgumentException("Invalid request type: " + requestType);
                        }
                    }
                    case "create_cart": {
                        if (requestType.equals("POST")) {

                        } else {
                            throw new IllegalArgumentException("Invalid request type: " + requestType);
                        }
                        break;
                    }
                    case "updateListOfPositions": {
                        if (requestType.equals("PUT")) {
                        } else {
                            throw new IllegalArgumentException("Invalid request type: " + requestType);
                        }
                        break;
                    }
                    case "rud_position": {
                        switch (requestType) {
                            case "PUT":
                                if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                    throw new IllegalArgumentException("Invalid role");
                                }
                                break;
                            case "GET":
                                break;
                            case "DELETE":
                                if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                    throw new IllegalArgumentException("Invalid role");
                                }
                                break;
                            default:
                                throw new IllegalArgumentException("Invalid request type: " + requestType);
                        }
                    }
                    case "create_position": {
                        if (requestType.equals("POST")) {
                            if (verifyResponse.getRole() != UserService.Roles.ADMIN) {
                                throw new IllegalArgumentException("Invalid role");
                            }
                        } else {
                            throw new IllegalArgumentException("Invalid request type: " + requestType);
                        }
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Invalid endpoint: " + endpoint);
                }
            }
            default:
                throw new IllegalArgumentException("Invalid service: " + serviceName);
        }

    }
}
