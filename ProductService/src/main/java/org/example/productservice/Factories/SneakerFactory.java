package org.example.productservice.Factories;

import lombok.AllArgsConstructor;
import com.sstore.productservice.ProductService;
import org.springframework.stereotype.Component;
import org.example.productservice.store.Enetities.SneakerEntity;

@Component
@AllArgsConstructor
public class SneakerFactory {

    private final CategoryFactory categoryFactory;

    public ProductService.SneakerResponse makeSneakerResponse(SneakerEntity sneakerEntity) {
        return ProductService.SneakerResponse.newBuilder()
                .setSneakerId(sneakerEntity.getSneakerId())
                .setSneakerName(sneakerEntity.getSneakerName())
                .setSneakerPrice(sneakerEntity.getSneakerPrice())
                .setLinkToPicture(sneakerEntity.getLinkToPicture())
                .setSneakerQuantity(sneakerEntity.getSneakerQuantity())
                .addAllCategories(sneakerEntity.getCategories().stream().map(categoryFactory::makeCategoryResponse).toList())
                .build();
    }
}
