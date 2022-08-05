package com.cg.model.dto;


import com.cg.model.LocationRegion;
import com.cg.model.Role;
import com.cg.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class UserDTO implements Validator {

    private String id;

    private String fullName;

    private String email;

    private String password;

    private String phone;

    private boolean isActive;

    private String urlImage;

    private RoleDTO roleDTO;

    private LocationRegionDTO locationRegion;


    public UserDTO(Long id, String email,String fullName) {
        this.id = id.toString();
        this.email = email;
        this.fullName = fullName;
    }

    public UserDTO(Long id, String email) {
        this.id = id.toString();
        this.email = email;
    }

    public UserDTO(Long id, String email, String password, Role role) {
        this.id = id.toString();
        this.email = email;
        this.password = password;
        this.roleDTO = role.toRoleDTO();
    }

    public UserDTO(Long id, String fullName, String email, String password, String phone, boolean isActive, String urlImage, Role role, LocationRegion locationRegion) {
        this.id = id.toString();
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.isActive = isActive;
        this.urlImage = urlImage;
        this.roleDTO = role.toRoleDTO();
        this.locationRegion = locationRegion.toLocationRegionDTO();
    }

    public User toUser() {
        return new User()
                .setId(Long.parseLong(id))
                .setFullName(fullName)
                .setEmail(email)
                .setPassword(password)
                .setPhone(phone)
                .setActive(isActive)
                .setUrlImage(urlImage)
                .setRole(roleDTO.toRole())
                .setLocationRegion(locationRegion.toLocationRegion());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDTO.class.isAssignableFrom(aClass);
    }
    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;

        String fullNameCheck = userDTO.getFullName();
        String emailCheck = userDTO.getEmail();
        String passwordCheck = userDTO.getPassword();
        String phoneCheck = userDTO.getPhone();

        if ((emailCheck.trim()).isEmpty()) {
            errors.rejectValue("email", "email.isEmpty", "Vui Lòng Nhập Email Người Dùng");
            return;
        }

        if ((fullNameCheck.trim().isEmpty())){
            errors.rejectValue("fullName", "fullName.isEmpty", "Vui Lòng Nhập Tên Người Dùng");
            return;
        }

        if ((passwordCheck.trim()).isEmpty()) {
            errors.rejectValue("password", "password.isEmpty", "Vui Lòng Nhập Mật Khẩu Người Dùng");
            return;
        }

        if ((phoneCheck.trim()).isEmpty()) {
            errors.rejectValue("phone", "phone.isEmpty", "Vui Lòng Nhập Số Điện Thoại Người Dùng");
            return;
        }

        if ((fullNameCheck.length() < 3 || fullNameCheck.length() > 255)) {
            errors.rejectValue("fullName", "fullName.length", "Tên Từ 3 Đến 255 Ký Tự");
            return;
        }

        if ((emailCheck.length() > 255)) {
            errors.rejectValue("email", "fullName.length", "Email Tối Đa 255 Ký Tự");
            return;
        }

        if (passwordCheck.length() > 50) {
            errors.rejectValue("password", "password.length", "Mật Khẩu Tối Đa 50 Ký Tự");
            return;
        }

        if (!emailCheck.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}$")) {
            errors.rejectValue("email", "email.matches", "Email Nhập Vào Không Hợp Lệ");
            return;
        }

//        if (!passwordCheck.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^*])(?!.*['\"`]).{6,}")) {
//            errors.rejectValue("password", "password.matches", "Mật Khẩu Nhập Vào Không Hợp Lệ");
//            return;
//        }

        if (!phoneCheck.matches("^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$")){
            errors.rejectValue("phone", "phone.matches", "Số Điện Thoại Nhập Vào Không Hợp Lệ");
            return;
        }
    }
}
