package org.example.productservice.store.Repositories;

import org.example.productservice.store.Enetities.SneakerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SneakerRepository extends JpaRepository<SneakerEntity, Long> {

    @Query("SELECT s FROM SneakerEntity s JOIN s.categories c " +
            "WHERE s.sneakerPrice BETWEEN :fromPrice AND :toPrice " +
            "AND c.categoryName IN :categories " +
            "GROUP BY s " +
            "HAVING COUNT(DISTINCT c.categoryName) = :categoryCount")
    List<SneakerEntity> findSneakersByPriceRangeAndCategories(
            @Param("fromPrice") Integer fromPrice,
            @Param("toPrice") Integer toPrice,
            @Param("categories") List<String> categories,
            @Param("categoryCount") Long categoryCount);
}
