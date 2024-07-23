package org.example.productservice.Services;


import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.example.productservice.Factories.SneakerFactory;
import com.sstore.productservice.ProductService;
import org.example.productservice.store.Enetities.SneakerEntity;
import org.example.productservice.store.Repositories.CategoryRepository;
import org.example.productservice.store.Repositories.SneakerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SneakerService {
    private final SneakerRepository sneakerRepository;
    private final SneakerFactory sneakerFactory;
    private final CategoryRepository categoryRepository;

    public void getSneaker(
            ProductService.SneakerId request,
            StreamObserver<ProductService.SneakerResponse> response) {
        // todo :: написать исключение
        try {
            ProductService.SneakerResponse response1 =
                    sneakerFactory.makeSneakerResponse(sneakerRepository.findById(request.getSneakerId()).orElseThrow());
            response.onNext(response1);
            response.onCompleted();
        } catch (Exception e) {
            response.onError(e);
        }
    }

    public void addSneaker(
            ProductService.SneakerRequest request,
            StreamObserver<ProductService.SneakerResponse> responseObserver) {
        try {
            SneakerEntity sneakerEntity = SneakerEntity.builder()
                    .sneakerName(request.getSneakerName())
                    .sneakerPrice(request.getSneakerPrice())
                    .sneakerQuantity(request.getSneakerQuantity())
                    .linkToPicture(request.getLinkToPicture())
                    .categories(
                            request.getCategoriesList().stream()
                                    .map(categoryRepository::findByCategoryName)
                                    // todo: пробросить нормально
                                    .map(Optional::orElseThrow)
                                    .toList())
                    .build();
            var a = sneakerRepository.saveAndFlush(sneakerEntity);
            responseObserver.onNext(sneakerFactory.makeSneakerResponse(a));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }

    }

    public void updateSneaker(
            ProductService.SneakerRequestWithId request,
            StreamObserver<ProductService.SneakerResponse> responseObserver) {
        try {
            // todo: нормальное исключение
            SneakerEntity oldSneaker = sneakerRepository.findById(request.getSneakerId()).orElseThrow();
            if (!request.getSneakerRequest().getSneakerName().isEmpty()) {
                oldSneaker.setSneakerName(request.getSneakerRequest().getSneakerName());
            }
            oldSneaker.setSneakerPrice(request.getSneakerRequest().getSneakerPrice());

            oldSneaker.setSneakerQuantity(request.getSneakerRequest().getSneakerQuantity());

            oldSneaker.setCategories(
                    request.getSneakerRequest().getCategoriesList().stream()
                            .map(categoryRepository::findByCategoryName)
                            // todo: пробросить нормально
                            .map(Optional::orElseThrow)
                            .toList());

            oldSneaker.setLinkToPicture(request.getSneakerRequest().getLinkToPicture());

            responseObserver.onNext(sneakerFactory
                    .makeSneakerResponse(sneakerRepository.saveAndFlush(oldSneaker)));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    public void deleteSneaker(ProductService.SneakerId request,
                              StreamObserver<com.google.protobuf.Empty> responseObserver) {
        try {
            if (sneakerRepository.existsById(request.getSneakerId())) {
                throw new RuntimeException();
            }
            sneakerRepository.deleteById(request.getSneakerId());
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    public void getAllSneakersFilter(
            ProductService.FilterRequest request,
            StreamObserver<ProductService.SneakerResponse> responseObserver) {
        try {
            List<SneakerEntity> sneakers;
            if (request.isInitialized()) {
                sneakers = sneakerRepository.findSneakersByPriceRangeAndCategories(
                        request.getFromPrice(),
                        request.getToPrice(),
                        request.getCategoryToFindList(),
                        (long) request.getCategoryToFindList().size());
            } else {
                sneakers = sneakerRepository.findAll();
            }
            int start = Math.max(request.getPage() * request.getSize(), 0);
            int end = Math.min(start + request.getSize(), sneakers.size());

            sneakers.subList(start, end).stream()
                    .map(sneakerFactory::makeSneakerResponse)
                    .forEach(responseObserver::onNext);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }

    }

    public void changeQuantity(ProductService.SneakerIdQuantity request,
                               StreamObserver<ProductService.SneakerResponse> responseObserver) {
        // todo: 404
        try {
            SneakerEntity sneaker = sneakerRepository.findById(request.getSneakerId()).orElseThrow();
            sneaker.setSneakerQuantity(request.getQuantity());
            responseObserver.onNext(sneakerFactory
                    .makeSneakerResponse(sneakerRepository.saveAndFlush(sneaker)));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }

    }

}
