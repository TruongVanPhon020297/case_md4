package com.cg.controller.rest;

import com.cg.exception.DataInputException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.model.dto.ProductDTO;
import com.cg.model.dto.SearchDTO;
import com.cg.model.dto.UserDTO;
import com.cg.service.product.ProductService;
import com.cg.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.math.BigDecimal;
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

    @PostMapping("/search")
    public ResponseEntity<?> searchProduct(@RequestBody String keySearch) {

        String key = keySearch.substring(1,keySearch.length()-1);

        if (key.trim().equals("")) {
            throw new DataInputException("Vui Lòng Nhập Tên Sản Phẩm Cần Tìm");
        }

        List<ProductDTO> productDTOList = productService.findProductDTOByTitle(key);

        if (productDTOList.isEmpty()) {
            throw new DataInputException("Không Tìm Thấy Từ Khóa");
        }

        return new ResponseEntity<>(productDTOList,HttpStatus.OK);
    }

    @PostMapping("/search-range")
    public ResponseEntity<?> searchProductByRange(@RequestBody SearchDTO searchDTO) {

        String valueUp = searchDTO.getValueUp();
        String valueDown = searchDTO.getValueDown();
        String keySearch = searchDTO.getKeySearch();

        List<ProductDTO> productDTOList;

        if (Long.parseLong(valueUp) < Long.parseLong(valueDown)){
            productDTOList = productService.findProductDTOByRange(keySearch,new BigDecimal(valueUp),new BigDecimal(valueDown));
        }else {
            productDTOList = productService.findProductDTOByRange(keySearch,new BigDecimal(valueDown),new BigDecimal(valueUp));
        }

        if (productDTOList.isEmpty()){
            throw new DataInputException("Không Tìm Thấy Sản Phẩm Nào Phù Hợp");
        }

        return new ResponseEntity<>(productDTOList,HttpStatus.OK);
    }

}
