package com.sstore.userservice.Factories;

import com.sstore.userservice.store.Entities.UserEntity;
import com.sstore.userservice.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    public UserService.UserResponse makeUserResponse(UserEntity userEntity) {
        return UserService.UserResponse.newBuilder()
                .setUserId(userEntity.getId())
                .setUsername(userEntity.getUsername())
                .build();
    }
}
