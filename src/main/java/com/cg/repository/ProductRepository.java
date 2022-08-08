package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
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

    @Query("SELECT new com.cg.model.dto.ProductDTO (" +
            "p.id, " +
            "p.title, " +
            "p.price, " +
            "p.quantity, " +
            "p.urlImage, " +
            "p.category" +
            ") " +
            "FROM Product AS p " +
            "WHERE p.title LIKE %?1% " +
            "ORDER BY p.title DESC"
    )
    List<ProductDTO> findProductDTOByTitle(String keySearch);

    @Query("SELECT new com.cg.model.dto.ProductDTO (" +
            "p.id, " +
            "p.title, " +
            "p.price, " +
            "p.quantity, " +
            "p.urlImage, " +
            "p.category" +
            ") " +
            "FROM Product AS p " +
            "WHERE p.title LIKE %?1% " +
            "AND p.price BETWEEN ?2 AND ?3"
    )
    List<ProductDTO> findProductDTOByRange(String keySearch, BigDecimal valueUp, BigDecimal valueDown);
}
