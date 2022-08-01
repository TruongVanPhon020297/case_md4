package com.cg.controller.rest;

import com.cg.exception.ResourceNotFoundException;
import com.cg.model.Cart;
import com.cg.model.dto.CartDTO;
import com.cg.model.dto.CartInfoDTO;
import com.cg.model.dto.ProductDTO;
import com.cg.model.dto.UserDTO;
import com.cg.service.cart.CartService;
import com.cg.service.product.ProductService;
import com.cg.service.user.UserService;
import com.cg.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    private AppUtil appUtil;

    @PostMapping("/add")
    public ResponseEntity<?> register(@Valid @RequestBody CartDTO cartDTO, BindingResult bindingResult) {
        new CartDTO().validate(cartDTO, bindingResult);
        if (bindingResult.hasFieldErrors()){
            return appUtil.mapErrorToResponse(bindingResult);
        }

        Optional<UserDTO> userDTOOptional = userService.findUserDTOById(cartDTO.getUserId());

        if (!userDTOOptional.isPresent()){
            throw new ResourceNotFoundException("Không Tìm Thấy Người Dùng");
        }

        Optional<ProductDTO> productDTOOptional = productService.getProductDTOById(Long.parseLong(cartDTO.getProductId()));

        if (!productDTOOptional.isPresent()) {
            throw new ResourceNotFoundException("Không Tìm Thấy Sản Phẩm");
        }


        String userId = userDTOOptional.get().getId();

        Optional<CartInfoDTO> cartInfoDTOOptional = cartService.findUserDTOByUserId(userId);

//        BigDecimal grandTotal = Bì

        Cart cart = new Cart();
        cart.setUser(userDTOOptional.get().toUser());


        if (!cartInfoDTOOptional.isPresent()) {
            cartService.addCart();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
