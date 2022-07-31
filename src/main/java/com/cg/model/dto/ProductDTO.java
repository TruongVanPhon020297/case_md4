package com.cg.model.dto;

import com.cg.model.Category;
import com.cg.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ProductDTO implements Validator {
    private String id;
    private String title;
    private String price;
    private String quantity;
    private String urlImage;
    private CategoryDTO categoryDTO;

    public Product toProduct() {
        return new Product()
                .setId(Long.parseLong(id))
                .setTitle(title)
                .setPrice(new BigDecimal(Long.parseLong(price)))
                .setQuantity(Integer.parseInt(quantity))
                .setUrlImage(urlImage)
                .setCategory(categoryDTO.toCategory());
    }

    public ProductDTO(Long id, String title, BigDecimal price, Integer quantity, String urlImage, Category category) {
        this.id = id.toString();
        this.title = title;
        this.price = price.toString();
        this.quantity = quantity.toString();
        this.urlImage = urlImage;
        this.categoryDTO = category.toCategoryDTO();
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
