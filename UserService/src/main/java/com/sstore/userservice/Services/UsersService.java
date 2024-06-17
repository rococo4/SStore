package com.sstore.userservice.Services;

import com.sstore.userservice.store.Entities.Roles;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.google.protobuf.Empty;
import com.sstore.userservice.Factories.UserFactory;
import com.sstore.userservice.store.Entities.UserEntity;
import com.sstore.userservice.store.Repositories.UserRepository;
import io.grpc.stub.StreamObserver;
import kafka.utils.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.example.userservice.UserService;
import org.hibernate.annotations.Cache;
import org.springframework.stereotype.Component;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class UsersService {
    private final UserRepository userRepository;
    private final UserFactory userFactory;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void signUp(UserService.SignUpRequest request,
                       StreamObserver<UserService.UserResponse> responseObserver) {
        try {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException();
            }
            if (userRepository.existsByUsername(request.getLogin())) {
                throw new RuntimeException();
            }

            responseObserver.onNext(userFactory.makeUserResponse(
                    userRepository.saveAndFlush(
                            UserEntity.builder()
                            .email(request.getEmail())
                            .password(passwordEncoder.encode(request.getPassword()))
                            .role(Roles.USER)
                            .build()
                    )
            ));
            responseObserver.onCompleted();
        } catch (RuntimeException e) {
            responseObserver.onError(e);
        }

    }

    public void signIn(UserService.SignInRequest request,
                       StreamObserver<UserService.UserResponse> responseObserver) {
        try {
            UserEntity user = userRepository.findByUsername(request.getLogin()).orElseThrow();
            if (passwordEncoder.matches(user.getPassword(),
                    passwordEncoder.encode(request.getPassword()))) {
                responseObserver.onNext(userFactory.makeUserResponse(user));
                responseObserver.onCompleted();
            } else {
                throw new RuntimeException("Wrong password");
            }
        } catch (RuntimeException e) {
            responseObserver.onError(e);
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
            if(!request.getEmail().isEmpty()) {
                if (!userRepository.existsByEmail(request.getEmail())) {
                    user.setEmail(request.getEmail());
                } else {
                    throw new RuntimeException("Email already exists");
                }

            }
            if (!request.getPassword().isEmpty()) {
                if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
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
}
