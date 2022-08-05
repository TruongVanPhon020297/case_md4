package com.cg.controller.rest;

import com.cg.exception.DataInputException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.model.dto.*;
import com.cg.service.CartItem.CartItemService;
import com.cg.service.cart.CartService;
import com.cg.service.order.OrderService;
import com.cg.service.product.ProductService;
import com.cg.service.user.UserService;
import com.cg.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    OrderService orderService;
    @Autowired
    private AppUtil appUtil;

    private String getPrincipal() {
        String email = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        email = ((UserDetails) principal).getUsername();
        return email;
    }

    private UserDTO getUserDTO(){
        String email = getPrincipal();
        Optional<UserDTO> userDTOOptional = userService.findUserDTOByEmail(email);
        return userDTOOptional.get();
    }

    @PostMapping("/add")
    public ResponseEntity<?> doAddOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult bindingResult){
        new OrderDTO().validate(orderDTO, bindingResult);

        if (bindingResult.hasFieldErrors()){
            return appUtil.mapErrorToResponse(bindingResult);
        }

        Optional<UserDTO> userDTOOptional = userService.findUserDTOById(Long.parseLong(orderDTO.getUserId()));

        if (!userDTOOptional.isPresent()){
            throw new ResourceNotFoundException("Không Tìm Thấy Người Dùng");
        }

        UserDTO userDTOLogin = getUserDTO();

        if (!(userDTOOptional.get().getId()).equals(userDTOLogin.getId())){
            throw new ResourceNotFoundException("Không Phải Người Dùng Đang Đăng Nhập Thao Tác");
        }

        String userId = userDTOOptional.get().getId();

        Optional<CartInfoDTO> cartInfoDTOOptional = cartService.findCartInfoDTOByUserId(Long.parseLong(userId));

        Map<String, Object> result = new HashMap<>();

        String success;

        if (!cartInfoDTOOptional.isPresent()) {
            throw new ResourceNotFoundException("Người Dùng Chưa Có Giỏ Hàng Để Đặt Hàng");
        }else {
            List<CartItemDTO> cartItemDTOList = cartItemService.findAllCartItemByCartId(Long.parseLong(cartInfoDTOOptional.get().getId()));
            if (cartItemDTOList.isEmpty()) {
                throw new ResourceNotFoundException("Người Dùng Chưa Có Sản Phẩm Trong Giỏ Hàng Để Đặt Hàng");
            }else {
                try{
                    orderService.doCreateOrder(orderDTO,cartInfoDTOOptional.get());
                    success = "Tạo Đơn Hàng Thành Công";
                    result.put("success",success);
                }catch (DataIntegrityViolationException e){
                    throw new DataInputException("Liên Hệ Chủ Cửa Hàng Để Được Giải Quyết");
                }
            }
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
