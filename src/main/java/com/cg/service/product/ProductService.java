package com.cg.service.product;

import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import com.cg.service.IGeneralService;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService extends IGeneralService<Product> {
    List<ProductDTO> findAllProductDTO();
    Optional<ProductDTO> getProductDTOById(Long id);
    List<ProductDTO> findProductDTOByTitle(String keySearch);
    List<ProductDTO> findProductDTOByRange(String keySearch, BigDecimal valueUp, BigDecimal valueDown);
}
