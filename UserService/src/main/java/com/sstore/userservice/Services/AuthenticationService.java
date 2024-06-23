//package com.sstore.userservice.Services;
//
//import com.sstore.gatewayservice.Requests.SignInRequest;
//import com.sstore.getwayservice.Requests.SignUpRequest;
//import com.sstore.getwayservice.Responses.JwtResponse;
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//import lombok.RequiredArgsConstructor;
//import org.example.userservice.UserControllerGrpc;
//import org.example.userservice.UserService;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//
//@RequiredArgsConstructor
//@Component
//public class AuthenticationService {
//    @Value("${UserService.grpc.server.port}")
//    private int userGrpcPort;
//    private final JwtService jwtService;
//    @PutMapping("/insecure/sign-in")
//    public JwtResponse SignIn(SignInRequest user) {
//        UserService.SignInRequest request = UserService.SignInRequest.newBuilder()
//                .setLogin(user.getUsername())
//                .setPassword(user.getPassword())
//                .build();
//        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", userGrpcPort)
//                .usePlaintext().build();
//        UserControllerGrpc.UserControllerBlockingStub stub = UserControllerGrpc.
//                newBlockingStub(channel);
//        try {
//            UserService.UserResponse response = stub.signIn(request);
//            return JwtResponse.builder().token(jwtService.generateToken(response)).build();
//        } catch (RuntimeException e) {
//            // todo: вывести адекватный exception
//        }
//        finally {
//            channel.shutdownNow();
//        }
//        return null;
//    }
//    @PostMapping("/insecure/sign-up")
//    public JwtResponse SignUp(SignUpRequest user) {
//        UserService.SignUpRequest request = UserService.SignUpRequest.newBuilder()
//                .setLogin(user.getUsername())
//                .setPassword(user.getPassword())
//                .setEmail(user.getEmail())
//                .build();
//        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", userGrpcPort)
//                .usePlaintext().build();
//        UserControllerGrpc.UserControllerBlockingStub stub = UserControllerGrpc.
//                newBlockingStub(channel);
//        try {
//            UserService.UserResponse response = stub.signUp(request);
//            return JwtResponse.builder().token(jwtService.generateToken(response)).build();
//        } catch (RuntimeException e) {
//            // todo: вывести адекватный exception
//        }
//        finally {
//            channel.shutdownNow();
//        }
//        return null;
//    }
//}
