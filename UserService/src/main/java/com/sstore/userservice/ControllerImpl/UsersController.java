package com.sstore.userservice.ControllerImpl;

import com.google.protobuf.Empty;
import com.sstore.userservice.Factories.UserFactory;
import com.sstore.userservice.Services.JwtService;
import com.sstore.userservice.Services.UsersService;
import com.sstore.userservice.config.PasswordEncoderConfig;
import com.sstore.userservice.store.Entities.UserEntity;
import com.sstore.userservice.store.Repositories.UserRepository;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import com.sstore.userservice.UserControllerGrpc;
import com.sstore.userservice.UserService;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@GrpcService
public class UsersController extends UserControllerGrpc.UserControllerImplBase {
    private static final Logger log = LoggerFactory.getLogger(UsersController.class);
    private final UsersService userService;
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final JwtService jwtService;

    @Override
    public void signUp(UserService.SignUpRequest request,
                       StreamObserver<UserService.JwtResponse> responseObserver) {

    }
    @Override
    public void signIn(UserService.SignInRequest request,
                       StreamObserver<UserService.JwtResponse> responseObserver) {
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
