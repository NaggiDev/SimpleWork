package com.example.simplework.controller.implement;

import com.example.simplework.controller.ProductInterface;
import com.example.simplework.entity.dto.ProductDTO;
import com.example.simplework.factory.response.ResponseFactory;
import com.example.simplework.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController implements ProductInterface {
    @Autowired
    ProductService productService;
    @Autowired
    ResponseFactory factory;

    public ResponseEntity<Object> getProduct(Integer id, String name) {
        return factory.success(productService.getAllProduct(id, name));
    }

    public ResponseEntity<Object> createProduct(ProductDTO product) {
        return factory.success(productService.createProduct(product));
    }

    public ResponseEntity<Object> updateProduct(Integer id, ProductDTO product) {
        return factory.success(productService.updateById(id, product));
    }

    public ResponseEntity<Object> deleteProduct(Integer id) {
        productService.deleteProduct(id);
        return factory.success();
    }
}
