package com.cg.model;


import com.cg.model.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
@Accessors(chain = true)
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url_image",nullable = false)
    private String urlImage;

    @Column(name = "stop_selling",columnDefinition = "boolean default false")
    private boolean stopSelling;

    @Column(nullable = false)
    private String title;

    @Digits(integer = 12, fraction = 0)
    private BigDecimal price;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<CartItem> cartItems;

    public ProductDTO toProductDTO() {
        return new ProductDTO()
                .setId(id.toString())
                .setTitle(title)
                .setPrice(price.toString())
                .setQuantity(String.valueOf(quantity))
                .setUrlImage(urlImage)
                .setCategoryDTO(category.toCategoryDTO());
    }
}
