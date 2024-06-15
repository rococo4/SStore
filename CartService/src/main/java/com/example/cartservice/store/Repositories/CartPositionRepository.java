package com.example.cartservice.store.Repositories;

import com.example.cartservice.store.Entities.CartPositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartPositionRepository extends JpaRepository<CartPositionEntity, Long> {
}
