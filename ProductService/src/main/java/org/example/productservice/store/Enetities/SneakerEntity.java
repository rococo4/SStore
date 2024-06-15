package org.example.productservice.store.Enetities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sneakers")
public class SneakerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long sneakerId;

    private Double sneakerPrice;
    private String sneakerName;
    private String linkToPicture;
    private Integer sneakerQuantity;

    @ManyToMany
    @JoinTable(
            name = "sneaker_category",
            joinColumns = @JoinColumn(name = "sneaker_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<CategoryEntity> categories;
}
