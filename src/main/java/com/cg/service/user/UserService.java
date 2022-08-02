package com.cg.service.user;

import com.cg.model.Product;
import com.cg.model.User;
import com.cg.model.dto.UserDTO;
import com.cg.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends IGeneralService<User>, UserDetailsService {
    User getByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<UserDTO> findUserDTOByEmail(String email);

    Optional<UserDTO> findUserDTOByEmailPassword(String email);

    Optional<UserDTO> findUserDTOByPhone(String phone);

    Optional<UserDTO> findUserDTOById(long id);
}
