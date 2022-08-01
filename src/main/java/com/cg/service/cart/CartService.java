package com.cg.service.cart;

import com.cg.model.Cart;
import com.cg.model.Product;
import com.cg.model.dto.CartInfoDTO;
import com.cg.model.dto.ProductDTO;
import com.cg.model.dto.UserDTO;
import com.cg.service.IGeneralService;

import java.util.Optional;

public interface CartService extends IGeneralService<Cart> {
    Optional<CartInfoDTO> findUserDTOByUserId(String id);
    void addCart();
}
