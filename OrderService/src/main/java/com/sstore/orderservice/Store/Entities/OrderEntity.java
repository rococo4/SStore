package com.sstore.orderservice.Store.Entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.orderservice.OrderService;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "create_at")
    private Instant createdAt;

    @Column(name = "state_of_order")
    private OrderService.StateOrder stateOfOrder;
}
