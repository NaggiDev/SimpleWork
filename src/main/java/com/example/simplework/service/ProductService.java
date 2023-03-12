package com.example.simplework.service;

import com.example.simplework.entity.dto.ProductDTO;
import com.example.simplework.entity.model.Product;
import com.example.simplework.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProduct(Integer id, String name) {
        if (name.isEmpty()) return productRepository.getAllById(id);
        return productRepository.getAllByProductName(name);
    }

    public Product saveOrUpdate(ProductDTO product) {
        Product p = new Product();
        p.setId(product.getId());
        p.setProductName(product.getProductName());
        p.setYear(Calendar.getInstance().getTime());
        p.setPrice(product.getPrice());
        p.setUrl(product.getUrl());
        return productRepository.save(p);
    }

    public Product updateById(Integer id, ProductDTO productDTO) {
        Product product = productRepository.getById(id);
        if (!Objects.isNull(product)) {
            product.setPrice(productDTO.getPrice());
            product.setProductName(productDTO.getProductName());
            product.setYear(Calendar.getInstance().getTime());
            product.setUrl(productDTO.getUrl());
            return productRepository.save(product);
        }
        return product;
    }

    public void deleteProduct(Integer id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            log.info("Delete fail");
        }
    }
}