package com.sstore.getwayservice.Controllers;

import com.sstore.getwayservice.Services.VerifyService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.example.userservice.UserControllerGrpc;
import org.example.userservice.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final VerifyService verifyService;
    private final SecurityFilterChain defaultSecurityFilterChain;

    @PostMapping("/secure")
    public Object secure(@RequestHeader("jwt") String token,
                         @RequestHeader("name") String serviceName,
                         @RequestHeader("request-type") String requestType,
                         @RequestHeader("endpoint") String endpoint,
                         @RequestBody Object body) {
        UserService.VerifyResponse verifyResponse = verifyService.verify(token);
        switch (serviceName) {
            case "ProductService":
                switch (endpoint) {
                    case "rud_category":
                        switch (requestType) {
                            case "PUT":
                                break;
                            case "GET":
                                break;
                            case "DELETE":
                                break;
                            default:
                                throw new IllegalArgumentException("Invalid request type: " + requestType);
                        }
                    case "create_category": {
                        if (requestType.equals("POST")) {
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
                                break;
                            case "GET":
                                break;
                            case "DELETE":
                                break;
                            default:
                                throw new IllegalArgumentException("Invalid request type: " + requestType);
                        }
                        break;
                    }
                    case "create_sneaker" : {
                        if (requestType.equals("POST")) {
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

                        } else {
                            throw new IllegalArgumentException("Invalid request type: " + requestType);
                        }
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Invalid endpoint: " + endpoint);
                }
            case "OrderService": {
                switch (endpoint) {
                    case "update_order" : {
                        if (requestType.equals("PUT")) {

                        } else {
                            throw new IllegalArgumentException("Invalid request type: " + requestType);
                        }
                    }
                    case "rud_order": {
                        switch (requestType) {
                            case "PUT":
                                break;
                            case "GET":
                        }
                    }
                }
            }

        }

    }
}
