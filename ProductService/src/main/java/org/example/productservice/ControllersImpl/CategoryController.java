package org.example.productservice.ControllersImpl;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.example.productservice.CategoryControllerGrpc;

import org.example.productservice.ProductService;
import org.example.productservice.Services.CategoryService;

@RequiredArgsConstructor
public class CategoryController extends CategoryControllerGrpc.CategoryControllerImplBase {

    private final CategoryService categoryService;
    @Override
    public void getCategory(ProductService.CategoryId request,
                             StreamObserver<ProductService.CategoryResponse> responseObserver) {
        categoryService.getCategory(request,responseObserver);
    }

    @Override
    public void updateCategory(ProductService.CategoryIdName request,
                                StreamObserver<ProductService.CategoryResponse> responseObserver) {
        categoryService.updateCategory(request,responseObserver);
    }

    @Override
    public void addCategory(ProductService.CategoryName request,
                             StreamObserver<ProductService.CategoryResponse> responseObserver) {
        categoryService.addCategory(request,responseObserver);
    }

    @Override
    public void deleteCategory(ProductService.CategoryId request,
                                StreamObserver<com.google.protobuf.Empty> responseObserver) {
        categoryService.deleteCategory(request,responseObserver);
    }

    @Override
    public void getAllCategory( com.google.protobuf.Empty request,
                                StreamObserver<ProductService.CategoryResponse> responseObserver) {
        categoryService.getAllCategory(responseObserver);
    }


}
