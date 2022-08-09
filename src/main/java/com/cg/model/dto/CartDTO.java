package com.cg.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CartDTO implements Validator {

    private String userId;

    private String productId;

    private String quantity;


    @Override
    public boolean supports(Class<?> aClass) {
        return CartDTO.class.isAssignableFrom(aClass);
    }
    @Override
    public void validate(Object target, Errors errors) {
        CartDTO cartDTO = (CartDTO) target;

        String userIdCheck = cartDTO.getUserId();
        String productIdCheck = cartDTO.getProductId();
        String quantityCheck = cartDTO.getQuantity();

        if ((userIdCheck.trim()).isEmpty()) {
            errors.rejectValue("userId", "userId.isEmpty", "Vui Lòng Cung Cấp Id Người Dùng");
            return;
        }

        if ((productIdCheck.trim().isEmpty())){
            errors.rejectValue("productId", "productId.isEmpty", "Vui Lòng Cung Cấp Id Sản Phẩm");
            return;
        }

        if ((quantityCheck.trim()).isEmpty()) {
            errors.rejectValue("quantity", "quantity.isEmpty", "Vui Lòng Cung Cấp Số Lượng Của Sản Phẩm");
            return;
        }

        if (!userIdCheck.matches("(^$|[0-9]*$)")) {
            errors.rejectValue("userId", "userId.matches", "Id Khách Hàng Không Hợp Lệ");
            return;
        }

        if (!productIdCheck.matches("(^$|[0-9]*$)")) {
            errors.rejectValue("productId", "productId.matches", "Id Sản Phẩm Không Hợp Lệ");
            return;
        }

        if (!quantityCheck.matches("(^$|[0-9]*$)")) {
            errors.rejectValue("quantity", "quantity.matches", "Số Lượng Sản Phẩm Không Hợp Lệ");
            return;
        }
    }
}
