package com.sstore.getwayservice.Services;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.sstore.userservice.UserControllerGrpc;
import com.sstore.userservice.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class VerifyService {
    @Value("${UserService.grpc.server.port}")
    private int userServicePort;

    public UserService.VerifyResponse verify(String token) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", userServicePort)
                .usePlaintext().build();
        UserControllerGrpc.UserControllerBlockingStub blockingStub =
                UserControllerGrpc.newBlockingStub(channel);
        UserService.VerifyRequest verifyRequest = UserService.VerifyRequest.newBuilder()
                .setJwt(token)
                .build();
        return blockingStub.verify(verifyRequest);
    }
}
