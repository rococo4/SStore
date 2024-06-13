package org.example.productservice.ControllersImpl;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.example.productservice.ProductService;
import org.example.productservice.Services.SneakerService;
import org.example.productservice.SneakerControllerGrpc;

@RequiredArgsConstructor
public class SneakerController extends SneakerControllerGrpc.SneakerControllerImplBase {

    private final SneakerService sneakerService;
    @Override
    public void updateSneaker(ProductService.SneakerRequestWithId request,
                              StreamObserver<ProductService.SneakerResponse> responseObserver) {
        sneakerService.updateSneaker(request, responseObserver);
    }

    @Override
    public void addSneaker(ProductService.SneakerRequest request,
                           StreamObserver<ProductService.SneakerResponse> responseObserver) {
        sneakerService.addSneaker(request, responseObserver);
    }

    @Override
    public void deleteSneaker(ProductService.SneakerId request,
                              StreamObserver<Empty> responseObserver) {
        sneakerService.deleteSneaker(request, responseObserver);
    }

    @Override
    public void getAllSneakersFilter(ProductService.FilterRequest request,
                                     StreamObserver<ProductService.SneakerResponse> responseObserver) {
        sneakerService.getAllSneakersFilter(request, responseObserver);
    }

    @Override
    public void changeQuantity(ProductService.SneakerIdQuantity request,
                               StreamObserver<ProductService.SneakerResponse> responseObserver) {
        sneakerService.changeQuantity(request, responseObserver);
    }

    @Override
    public void getSneaker(ProductService.SneakerId request,
                           StreamObserver<ProductService.SneakerResponse> response) {
        sneakerService.getSneaker(request, response);
    }

}
