package com.cg.service.cart;

import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.model.Product;
import com.cg.model.dto.CartInfoDTO;
import com.cg.model.dto.CartItemDTO;
import com.cg.model.dto.ProductDTO;
import com.cg.model.dto.UserDTO;
import com.cg.service.IGeneralService;

import java.util.Optional;

public interface CartService extends IGeneralService<Cart> {
    Optional<CartInfoDTO> findCartInfoDTOByUserId(long id);
    CartItem addNewCart(Cart cart,CartItem cartItem);
    CartItem addNewProductByCart(Cart cart,CartItem cartItem);
    CartItem updateProductByCart(Cart cart, CartItem cartItem);
    CartInfoDTO doRemoveCartItem(Cart cart,Long cartItemId);
}
