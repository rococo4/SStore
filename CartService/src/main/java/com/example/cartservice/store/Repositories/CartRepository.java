package com.example.cartservice.store.Repositories;

import com.example.cartservice.store.Entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
}
