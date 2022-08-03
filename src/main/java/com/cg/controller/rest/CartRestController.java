package com.cg.controller.rest;

import com.cg.exception.DataInputException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.model.dto.*;
import com.cg.service.CartItem.CartItemService;
import com.cg.service.cart.CartService;
import com.cg.service.product.ProductService;
import com.cg.service.user.UserService;
import com.cg.util.AppUtil;
import com.sun.activation.registries.MailcapParseException;
import jdk.internal.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllCartItem(@PathVariable String id){

        Optional<UserDTO> userDTOOptional = userService.findUserDTOById(Long.parseLong(id));

        if (!userDTOOptional.isPresent()){
            throw new ResourceNotFoundException("Không Tìm Thấy Người Dùng");
        }

        String userId = userDTOOptional.get().getId();

        Optional<CartInfoDTO> cartInfoDTOOptional = cartService.findCartInfoDTOByUserId(Long.parseLong(userId));

        Map<String,String> result = new HashMap<>();

        if (!cartInfoDTOOptional.isPresent()) {
            result.put("noCart","Giỏ Hàng Của Bạn Đang Trống");
            return new ResponseEntity<>(result,HttpStatus.OK);
        }

        String cartId = cartInfoDTOOptional.get().getId();

        List<CartItemDTO> cartItemDTOList = cartItemService.findAllCartItemByCartId(Long.parseLong(cartId));

        return new ResponseEntity<>(cartItemDTOList,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCart(@Valid @RequestBody CartDTO cartDTO, BindingResult bindingResult) {
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
        Map<String, Object> result = new HashMap<>();

        String success;

        if (!cartInfoDTOOptional.isPresent()) {
            cart.setUser(userDTOOptional.get().toUser());
            cart.setGrandTotal(grandTotal);

            cartItem = new CartItem();
            cartItem.setPrice(new BigDecimal(Long.parseLong(productDTOOptional.get().getPrice())));
            cartItem.setQuantity(Integer.parseInt(cartDTO.getQuantity()));
            cartItem.setTitle(productDTOOptional.get().getTitle());
            cartItem.setTotalPrice(grandTotal);
            cartItem.setProduct(productDTOOptional.get().toProduct());

            try{
                cartService.addNewCart(cart,cartItem);
                success = "Tạo Giỏ Hàng Thành Công , Thêm Mới Sản Phẩm Thành Công";
                result.put("success", success);
            }catch (DataIntegrityViolationException e){
                throw new DataInputException("Liên Hệ Chủ Cửa Hàng Để Được Giải Quyết");
            }
        }else {
            String cartId = cartInfoDTOOptional.get().getId();
            String productId = productDTOOptional.get().getId();
            Optional<CartItemDTO> cartItemDTO = cartItemService.findCartItemDTOByCartIdAndProductId(Long.parseLong(cartId),Long.parseLong(productId));

            if (!cartItemDTO.isPresent()) {

                cartItem.setPrice(new BigDecimal(Long.parseLong(productDTOOptional.get().getPrice())));
                cartItem.setQuantity(Integer.parseInt(cartDTO.getQuantity()));
                cartItem.setTitle(productDTOOptional.get().getTitle());
                cartItem.setTotalPrice(grandTotal);
                cartItem.setProduct(productDTOOptional.get().toProduct());
                cart = cartInfoDTOOptional.get().toCart();
                cartItem.setCart(cart);
                cart.setGrandTotal(cart.getGrandTotal().add(grandTotal));
                try{
                    cartService.addNewProductByCart(cart,cartItem);
                    success ="Thêm Mới Sản Phẩm Thành Công";
                    result.put("success", success);
                }catch (DataIntegrityViolationException e){
                    throw new DataInputException("Liên Hệ Chủ Cửa Hàng Để Được Giải Quyết");
                }
                return new ResponseEntity<>(result,HttpStatus.CREATED);
            }else {
                cartItem = cartItemDTO.get().toCartItem();
                cartItem.setQuantity(cartItem.getQuantity() + Integer.parseInt(quantity));
                cartItem.setTotalPrice(cartItem.getTotalPrice().add(grandTotal));
                cart = cartInfoDTOOptional.get().toCart();
                cart.setGrandTotal(cart.getGrandTotal().add(grandTotal));
                try{
                    cartService.updateProductByCart(cart,cartItem);
                    success = new String("Cập Nhập Sản Phẩm Thành Công");
                    result.put("success", success);
                }catch (DataIntegrityViolationException e){
                    throw new DataInputException("Liên Hệ Chủ Cửa Hàng Để Được Giải Quyết");
                }
            }
        }
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }
}
