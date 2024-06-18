package com.sstore.userservice.store.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.userservice.UserService;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;

    private String password;
    private String username;
    private String email;
    private UserService.Roles role;
}
