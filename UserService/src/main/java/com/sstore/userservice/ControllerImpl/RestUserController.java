package com.sstore.userservice.ControllerImpl;

import com.sstore.userservice.Services.UsersService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.example.userservice.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
@RequiredArgsConstructor
public class RestUserController {
    private final UsersService userService;
    
    @PutMapping("/insecure/sign-in")
    public UserService.UserResponse signIn(@RequestBody UserService.SignInRequest signInRequest,
                                           StreamObserver<UserService.UserResponse> response) {
        final UserService.UserResponse[] userResponse = new UserService.UserResponse[1];
        final Throwable[] error = new Throwable[1];

        StreamObserver<UserService.UserResponse> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(UserService.UserResponse response) {
                userResponse[0] = response;
            }

            @Override
            public void onError(Throwable t) {
                error[0] = t;
            }

            @Override
            public void onCompleted() {

            }
        };

        userService.signIn(signInRequest, responseObserver);
        if (error[0] != null) {
            throw new RuntimeException("Error during signIn", error[0]);
        }

        return userResponse[0];
    }
    @PostMapping("/insecure/sign-up")
    public UserService.UserResponse signUp(@RequestBody UserService.SignUpRequest signUpRequest,
                                           StreamObserver<UserService.UserResponse> response) {
        final UserService.UserResponse[] userResponse = new UserService.UserResponse[1];
        final Throwable[] error = new Throwable[1];

        StreamObserver<UserService.UserResponse> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(UserService.UserResponse response) {
                userResponse[0] = response;
            }

            @Override
            public void onError(Throwable t) {
                error[0] = t;
            }

            @Override
            public void onCompleted() {

            }
        };

        userService.signUp(signUpRequest, responseObserver);
        if (error[0] != null) {
            throw new RuntimeException("Error during signUp", error[0]);
        }

        return userResponse[0];
    }


}
