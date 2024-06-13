package org.example.productservice.Services;


import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.example.productservice.Factories.CategoryFactory;
import org.example.productservice.ProductService;
import org.example.productservice.store.Enetities.CategoryEntity;
import org.example.productservice.store.Repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryFactory categoryFactory;

    public void getCategory(ProductService.CategoryId request,
                            StreamObserver<ProductService.CategoryResponse> responseObserver) {
        try {
            ProductService.CategoryResponse response = categoryFactory.makeCategoryResponse(
                    // todo: 404
                    categoryRepository.findById(request.getCategoryId()).orElseThrow()
            );
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }

    }

    public void updateCategory(
            ProductService.CategoryIdName request,
            StreamObserver<ProductService.CategoryResponse> responseObserver) {

        //todo: 404
        try {
            CategoryEntity categoryEntity = categoryRepository.findById(request.getCategoryId()).orElseThrow();
            categoryEntity.setCategoryName(request.getCategoryName());

            categoryRepository.saveAndFlush(categoryEntity);

            responseObserver.onNext(categoryFactory.makeCategoryResponse(categoryEntity));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    public void addCategory(
            ProductService.CategoryName request,
            StreamObserver<ProductService.CategoryResponse> responseObserver) {
        try {
            if (categoryRepository.existsByCategoryName(request.getCategoryName())) {
                // todo: BadRequest
            }
            CategoryEntity categoryEntity = CategoryEntity.builder().categoryName(request.getCategoryName()).build();
            categoryEntity = categoryRepository.saveAndFlush(categoryEntity);
            responseObserver.onNext(categoryFactory.makeCategoryResponse(categoryEntity));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }

    }


    public void deleteCategory(ProductService.CategoryId request,
                               StreamObserver<com.google.protobuf.Empty> responseObserver) {
        if (!categoryRepository.existsById(request.getCategoryId())) {
            //todo : exception
            throw new RuntimeException();
        }
        categoryRepository.deleteById(request.getCategoryId());
        responseObserver.onNext(com.google.protobuf.Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    public void getAllCategory(StreamObserver<ProductService.CategoryResponse> responseObserver) {
        List<ProductService.CategoryResponse> categories =
                categoryRepository.findAll().stream().
                        map(categoryFactory::makeCategoryResponse)
                        .toList();
        categories.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }
}
