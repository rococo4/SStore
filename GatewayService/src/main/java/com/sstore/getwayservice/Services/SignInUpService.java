package com.sstore.getwayservice.Services;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.userservice.UserControllerGrpc;
import org.example.userservice.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SignInUpService {
    @Value("${UserService.grpc.server.port}")
    private int userGrpcPort;
    public UserService.JwtResponse signIn(UserService.SignInRequest signInRequest) {
        ManagedChannel userChannel = ManagedChannelBuilder.forAddress("localhost", userGrpcPort)
                .usePlaintext().build();
        UserControllerGrpc.UserControllerBlockingStub stub = UserControllerGrpc.newBlockingStub(userChannel);
        return stub.signIn(signInRequest);
    }
    public UserService.JwtResponse signUp(UserService.SignUpRequest signUpRequest) {
        ManagedChannel userChannel = ManagedChannelBuilder.forAddress("localhost", userGrpcPort)
                .usePlaintext().build();
        UserControllerGrpc.UserControllerBlockingStub stub = UserControllerGrpc.newBlockingStub(userChannel);
        return stub.signUp(signUpRequest);
    }
}
