package com.sstore.getwayservice.Services;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.sstore.userservice.UserControllerGrpc;
import com.sstore.userservice.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
