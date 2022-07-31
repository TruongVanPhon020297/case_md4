package com.cg.controller.rest;

import com.cg.exception.ResourceNotFoundException;
import com.cg.model.dto.ProductDTO;
import com.cg.service.product.ProductService;
import com.cg.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {
    @Autowired
    AppUtil appUtil;

    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProduct() {
        List<ProductDTO> productDTOList = productService.findAllProductDTO();
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable String id) {
        Optional<ProductDTO> productDTOOptional = null;
        try{
             productDTOOptional = productService.getProductDTOById(Long.parseLong(id));
        }catch (NumberFormatException e) {
            throw new ResourceNotFoundException("ID Không Hợp Lệ");
        }

        if (!productDTOOptional.isPresent()) {
            throw new ResourceNotFoundException("Không Tìm Thấy Sản Phẩm");
        }
        return new ResponseEntity<>(productDTOOptional.get(), HttpStatus.OK);
    }

}
