package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT new com.cg.model.dto.ProductDTO (" +
            "p.id, " +
            "p.title, " +
            "p.price, " +
            "p.quantity, " +
            "p.urlImage, " +
            "p.category" +
            ") " +
            "FROM Product AS p"
    )
    List<ProductDTO> findAllProductDTO();

    @Query("SELECT new com.cg.model.dto.ProductDTO (" +
            "p.id, " +
            "p.title, " +
            "p.price, " +
            "p.quantity, " +
            "p.urlImage, " +
            "p.category" +
            ") " +
            "FROM Product AS p " +
            "WHERE p.id = :id"
    )
    Optional<ProductDTO> getProductDTOById(@Param("id") Long id);
}
