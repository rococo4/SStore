package org.example.productservice.Factories;

import org.example.productservice.store.Enetities.CategoryEntity;
import lombok.AllArgsConstructor;
import com.sstore.productservice.ProductService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryFactory {
    public ProductService.CategoryResponse makeCategoryResponse(CategoryEntity categoryEntity) {
        return ProductService.CategoryResponse.newBuilder()
                .setCategoryId(categoryEntity.getCategoryId())
                .setCategoryName(categoryEntity.getCategoryName())
                .build();
    }
}
