package com.cg.model.dto;

import com.cg.model.Cart;
import com.cg.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CartInfoDTO {
    private String id;

    private String grandTotal;

    private UserDTO user;

    public CartInfoDTO(Long id, BigDecimal grandTotal,User user) {
        this.id = id.toString();
        this.grandTotal = grandTotal.toString();
        this.user = user.toUserDTO();
    }

    public Cart toCart() {
        return new Cart()
                .setId(Long.parseLong(id))
                .setGrandTotal(new BigDecimal(grandTotal))
                .setUser(user.toUser());
    }
}
