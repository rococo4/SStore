package org.example.productservice.store.Repositories;

import org.example.productservice.store.Enetities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByCategoryName(String name);

    boolean existsByCategoryName(String name);
}
