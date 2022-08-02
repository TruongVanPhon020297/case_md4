package com.cg.repository;


import com.cg.model.Product;
import com.cg.model.User;
import com.cg.model.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User getByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("SELECT NEW com.cg.model.dto.UserDTO (u.id, u.email,u.fullName) FROM User u WHERE u.email = ?1")
    Optional<UserDTO> findUserDTOByEmail(String email);

    @Query("SELECT NEW com.cg.model.dto.UserDTO (u.id, u.email,u.password,u.role) FROM User u WHERE u.email = ?1")
    Optional<UserDTO> findUserDTOByEmailPassword(String email);

    @Query("SELECT NEW com.cg.model.dto.UserDTO (u.id, u.phone) FROM User u WHERE u.phone = ?1")
    Optional<UserDTO> findUserDTOByPhone(String phone);

    @Query("SELECT NEW com.cg.model.dto.UserDTO (u.id,u.fullName, u.email,u.password,u.phone,u.isActive,u.urlImage,u.role,u.locationRegion) FROM User u WHERE u.id = ?1")
    Optional<UserDTO> findUserDTOById(Long id);
}
