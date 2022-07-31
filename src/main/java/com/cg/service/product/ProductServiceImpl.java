package com.cg.service.product;


import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import com.cg.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;



    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Product getById(Long id) {
        return null;
    }

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public List<ProductDTO> findAllProductDTO() {
        List<ProductDTO> productDTOList = new ArrayList<>();
        return productDTOList = productRepository.findAllProductDTO();
    }

    @Override
    public Optional<ProductDTO> getProductDTOById(Long id) {
        return productRepository.getProductDTOById(id);
    }
}
