package com.sstore.userservice.Services;

import com.sstore.userservice.ControllerImpl.UsersController;
import com.sstore.userservice.config.PasswordEncoderConfig;
import com.sstore.userservice.store.Entities.Roles;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.google.protobuf.Empty;
import com.sstore.userservice.Factories.UserFactory;
import com.sstore.userservice.store.Entities.UserEntity;
import com.sstore.userservice.store.Repositories.UserRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import com.sstore.userservice.UserService;
import org.hibernate.annotations.Cache;
import org.springframework.stereotype.Component;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class UsersService  {
    private static final Logger log = LoggerFactory.getLogger(UsersController.class);
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final JwtService jwtService;

    public void signUp(UserService.SignUpRequest request,
                       StreamObserver<UserService.JwtResponse> responseObserver) {
        log.info("Sign up request received");
        try {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already in use");
            }
            if (userRepository.existsByUsername(request.getLogin())) {
                throw new RuntimeException("Username already in use");
            }

            UserEntity user = userRepository.saveAndFlush(
                    UserEntity.builder()
                            .email(request.getEmail())
                            .password(passwordEncoderConfig.getPasswordEncoder()
                                    .encode(request.getPassword()))
                            .role(UserService.Roles.USER)
                            .username(request.getLogin())
                            .build()
            );
            UserService.JwtResponse jwt = UserService.JwtResponse.newBuilder()
                    .setJwt(jwtService.generateToken(user)).build();
            responseObserver.onNext(jwt);
            responseObserver.onCompleted();
            log.info("Sign up successful {}", jwt);
        } catch (RuntimeException e) {
            log.info("Error caught {}", e.getMessage());
            Status status = Status.UNKNOWN.withDescription(e.getMessage()).withCause(e);
            responseObserver.onError(new StatusRuntimeException(status));
        }

    }

    public void signIn(UserService.SignInRequest request,
                       StreamObserver<UserService.JwtResponse> responseObserver) {

        try {
            UserEntity user = userRepository.findByUsername(request.getLogin()).orElseThrow();
            if (passwordEncoderConfig.getPasswordEncoder().matches(request.getPassword(),user.getPassword())) {
                responseObserver.onNext(UserService.JwtResponse.newBuilder()
                        .setJwt(jwtService.generateToken(user)).build());
                responseObserver.onCompleted();
            } else {
                throw new RuntimeException("Wrong password");
            }
        } catch (RuntimeException e) {
            log.info("Error caught {}", e.getMessage());
            Status status = Status.UNKNOWN.withDescription(e.getMessage()).withCause(e);
            responseObserver.onError(new StatusRuntimeException(status));
        }


    }

    public void getUserById(UserService.UserId request,
                            StreamObserver<UserService.UserResponse> responseObserver) {
        try {
            responseObserver.onNext(
                    userFactory.makeUserResponse(
                            userRepository.findById(request.getUserId()).orElseThrow()
                    )
            );
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }

    }

    public void updateUser(UserService.UpdateUserRequest request,
                           StreamObserver<UserService.UserResponse> responseObserver) {
        try {
            UserEntity user = userRepository.findById(request.getUserId()).orElseThrow();
            if (!request.getEmail().isEmpty()) {
                if (!userRepository.existsByEmail(request.getEmail())) {
                    user.setEmail(request.getEmail());
                } else {
                    throw new RuntimeException("Email already exists");
                }

            }
            if (!request.getPassword().isEmpty()) {
                if (!passwordEncoderConfig.getPasswordEncoder().matches(request.getPassword(),user.getPassword())) {
                    user.setPassword(request.getPassword());
                } else {
                    throw new RuntimeException("The same password");
                }
            }
            responseObserver.onNext(userFactory.makeUserResponse(user));
            responseObserver.onCompleted();
        } catch (RuntimeException e) {
            responseObserver.onError(e);
        }

    }

    public void deleteUser(UserService.UserId request,
                           StreamObserver<Empty> responseObserver) {
        try {
            if (!userRepository.existsById(request.getUserId())) {
                throw new RuntimeException();
            }
            userRepository.deleteById(request.getUserId());
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (RuntimeException e) {
            responseObserver.onError(e);
        }

    }

    public void updateUserRole(UserService.RoleRequest role,
                               StreamObserver<UserService.UserResponse> responseObserver) {
        try {
            UserEntity user = userRepository.findById(role.getUserId()).orElseThrow();
            user.setRole(role.getRole());
            responseObserver.onNext(
                    userFactory.makeUserResponse(
                            userRepository.saveAndFlush(user)
                    )
            );
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onCompleted();
        }
    }

    public void verify(UserService.VerifyRequest request,
                       StreamObserver<UserService.VerifyResponse> responseObserver) {
        try {
            String username = jwtService.getUsername(request.getJwt());
            UserEntity user = userRepository.findByUsername(username).orElseThrow();
            UserService.VerifyResponse response = UserService.VerifyResponse.newBuilder()
                    .setLogin(user.getUsername())
                    .setUserId(user.getId())
                    .setRole(user.getRole())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username).orElseThrow();
    }

}
