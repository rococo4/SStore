package com.example.cartservice.store.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Carts")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "cart_id")
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    @Column(name = "cart_positions")
    private List<CartPositionEntity> cartPositions;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "user_id")
    private Long userId;

}
