package org.example.productservice.ControllersImpl;

import io.grpc.stub.StreamObserver;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.example.productservice.ProductService;
import org.example.productservice.SneakerControllerGrpc;

public class SneakerController extends SneakerControllerGrpc.SneakerControllerImplBase {
    public void getSneaker(
            ProductService.SneakerRequest request,
            StreamObserver<ProductService.SneakerResponse> response) {

    }

    public void updateSneaker(
            ProductService.SneakerRequestWithId request,
            StreamObserver<ProductService.SneakerResponse> sneakerResponse) {

    }

    public void addSneaker(
            ProductService.SneakerRequest request,
            ProductService.SneakerResponse response) {

    }

    public void deleteSneaker(
            ProductService.SneakerId sneakerId,
            StreamObserver<>) {

    }

}
