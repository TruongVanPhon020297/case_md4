package com.cg.controller.rest;

import com.cg.exception.ResourceNotFoundException;
import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.model.dto.*;
import com.cg.service.CartItem.CartItemService;
import com.cg.service.cart.CartService;
import com.cg.service.product.ProductService;
import com.cg.service.user.UserService;
import com.cg.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartRestController {
    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    private AppUtil appUtil;

    @PostMapping("/add")
    public ResponseEntity<?> register(@Valid @RequestBody CartDTO cartDTO, BindingResult bindingResult) {
        new CartDTO().validate(cartDTO, bindingResult);
        if (bindingResult.hasFieldErrors()){
            return appUtil.mapErrorToResponse(bindingResult);
        }

        Optional<UserDTO> userDTOOptional = userService.findUserDTOById(Long.parseLong(cartDTO.getUserId()));

        if (!userDTOOptional.isPresent()){
            throw new ResourceNotFoundException("Không Tìm Thấy Người Dùng");
        }

        Optional<ProductDTO> productDTOOptional = productService.getProductDTOById(Long.parseLong(cartDTO.getProductId()));

        if (!productDTOOptional.isPresent()) {
            throw new ResourceNotFoundException("Không Tìm Thấy Sản Phẩm");
        }


        String userId = userDTOOptional.get().getId();

        Optional<CartInfoDTO> cartInfoDTOOptional = cartService.findCartInfoDTOByUserId(Long.parseLong(userId));

        String quantity = cartDTO.getQuantity();
        BigDecimal price = new BigDecimal(Long.parseLong(productDTOOptional.get().getPrice()));
        BigDecimal grandTotal = price.multiply(new BigDecimal(Long.parseLong(quantity)));

        CartItem cartItem = new CartItem();
        Cart cart = new Cart();

        if (!cartInfoDTOOptional.isPresent()) {
            cart = new Cart();
            cart.setUser(userDTOOptional.get().toUser());
            cart.setGrandTotal(grandTotal);

            cartItem = new CartItem();
            cartItem.setPrice(new BigDecimal(Long.parseLong(productDTOOptional.get().getPrice())));
            cartItem.setQuantity(Integer.parseInt(cartDTO.getQuantity()));
            cartItem.setTitle(productDTOOptional.get().getTitle());
            cartItem.setTotalPrice(grandTotal);
            cartItem.setProduct(productDTOOptional.get().toProduct());

            cartService.addNewCart(cart,cartItem);
            return new ResponseEntity<>("Tạo Giỏ Hàng Thành Công Thêm Mới Sản Phẩm Thành Công",HttpStatus.CREATED);
        }else {
            String cartId = cartInfoDTOOptional.get().getId();
            String productId = productDTOOptional.get().getId();
            Optional<CartItemDTO> cartItemDTO = cartItemService.findCartItemDTOByCartIdAndProductId(cartId,productId);

            if (!cartItemDTO.isPresent()) {

                cartItem.setPrice(new BigDecimal(Long.parseLong(productDTOOptional.get().getPrice())));
                cartItem.setQuantity(Integer.parseInt(cartDTO.getQuantity()));
                cartItem.setTitle(productDTOOptional.get().getTitle());
                cartItem.setTotalPrice(grandTotal);
                cartItem.setProduct(productDTOOptional.get().toProduct());
                cart = cartInfoDTOOptional.get().toCart();
                cartItem.setCart(cart);
                cart.setGrandTotal(cart.getGrandTotal().add(grandTotal));
                cartService.addNewProductByCart(cart,cartItem);
                return new ResponseEntity<>("Thêm Mới Sản Phẩm Thành Công",HttpStatus.CREATED);
            }else {
                cartItem = cartItemDTO.get().toCartItem();
                cartItem.setQuantity(cartItem.getQuantity() + Integer.parseInt(quantity));
                cartItem.setTotalPrice(cartItem.getTotalPrice().add(grandTotal));
                cart = cartInfoDTOOptional.get().toCart();
                cart.setGrandTotal(cart.getGrandTotal().add(grandTotal));
                cartService.updateProductByCart(cart,cartItem);
                return new ResponseEntity<>("Cập Nhập Sản Phẩm Thành Công",HttpStatus.CREATED);
            }
        }
    }
}
