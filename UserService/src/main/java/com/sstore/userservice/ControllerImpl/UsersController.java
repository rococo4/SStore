package com.sstore.userservice.ControllerImpl;

import com.google.protobuf.Empty;
import com.sstore.userservice.Services.UsersService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.example.userservice.UserControllerGrpc;
import org.example.userservice.UserService;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
public class UsersController extends UserControllerGrpc.UserControllerImplBase {
    private final UsersService userService;
    @Override
    public void signUp(UserService.SignUpRequest request,
                       StreamObserver<UserService.UserResponse> responseObserver) {
        userService.signUp(request, responseObserver);
    }
    @Override
    public void signIn(UserService.SignInRequest request,
                       StreamObserver<UserService.UserResponse> responseObserver) {
        userService.signIn(request, responseObserver);
    }
    @Override
    public void getUserById(UserService.UserId request,
                            StreamObserver<UserService.UserResponse> responseObserver) {
        userService.getUserById(request, responseObserver);
    }
    @Override
    public void updateUser(UserService.UpdateUserRequest request,
                           StreamObserver<UserService.UserResponse> responseObserver) {
        userService.updateUser(request, responseObserver);
    }
    @Override
    public void deleteUser(UserService.UserId request,
                           StreamObserver<Empty> responseObserver) {
        userService.deleteUser(request, responseObserver);
    }
    @Override
    public void updateUserRole(UserService.RoleRequest request,
                               StreamObserver<UserService.UserResponse> responseObserver) {
        userService.updateUserRole(request, responseObserver);
    }

    @Override
    public void verify(UserService.VerifyRequest request,
                       StreamObserver<UserService.VerifyResponse> responseObserver) {
        userService.verify(request, responseObserver);

    }

}
