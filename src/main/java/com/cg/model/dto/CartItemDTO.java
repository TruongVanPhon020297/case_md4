package com.cg.model.dto;


import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CartItemDTO {

    private String id;

    private String title;

    private String price;

    private String quantity;

    private String totalPrice;


    private ProductDTO product;


    private CartInfoDTO cart;

    public CartItemDTO(Long id, String title, BigDecimal price, int quantity, BigDecimal totalPrice, Product product, Cart cart) {
        this.id = id.toString();
        this.title = title;
        this.price = price.toString();
        this.quantity = String.valueOf(quantity);
        this.totalPrice = totalPrice.toString();
        this.product = product.toProductDTO();
        this.cart = cart.toCartInfoDTO();
    }

    public CartItem toCartItem() {
        return new CartItem()
                .setId(Long.parseLong(id))
                .setTitle(title)
                .setPrice(new BigDecimal(Long.parseLong(price)))
                .setQuantity(Integer.parseInt(quantity))
                .setTotalPrice(new BigDecimal(Long.parseLong(totalPrice)))
                .setProduct(product.toProduct())
                .setCart(cart.toCart());
    }
}
