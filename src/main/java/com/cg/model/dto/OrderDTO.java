package com.cg.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.Valid;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class OrderDTO implements Validator {

    private String userId;
    private LocationRegionDeliveryDTO locationRegion;
    private String deliveryDate;

    @Override
    public boolean supports(Class<?> aClass) {
        return OrderDTO.class.isAssignableFrom(aClass);
    }
    @Override
    public void validate(Object target, Errors errors) {
        OrderDTO orderDTO = (OrderDTO) target;

        String userIdCheck = orderDTO.getUserId();

        if ((userIdCheck.trim()).isEmpty()) {
            errors.rejectValue("userId", "userId.isEmpty", "Vui Lòng Cung Cấp Id Người Dùng");
            return;
        }
    }
}
