package com.example.cartservice.store.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CartPositions")
public class CartPositionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long quantity;

    @Column(name = "sneaker_id")
    private Long sneakerId;
}
