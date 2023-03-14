package com.example.simplework.service;

import com.example.simplework.entity.dto.ProductDTO;
import com.example.simplework.entity.model.Product;
import com.example.simplework.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Slf4j
@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProduct(Integer id, String name) {
        if (name.isEmpty()) return productRepository.getAllById(id);
        return productRepository.getAllByProductName(name);
    }

    private Product saveOrUpdate(Product product) {
        product.setUpdatedAt(Calendar.getInstance().getTime());
        return productRepository.save(product);
    }

    public Product createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(product.getId());
        product.setProductName(product.getProductName());
        product.setManufactureYear(product.getManufactureYear());
        product.setPrice(product.getPrice());
        product.setUrl(product.getUrl());
        product.setCreatedAt(Calendar.getInstance().getTime());
        return saveOrUpdate(product);
    }

    public Product updateById(Integer id, ProductDTO productDTO) {
        Product product = productRepository.getById(id);
        product.setPrice(productDTO.getPrice());
        product.setProductName(productDTO.getProductName());
        product.setManufactureYear(Calendar.getInstance().getTime());
        product.setUrl(productDTO.getUrl());
        return saveOrUpdate(product);
    }

    public void deleteProduct(Integer id) {
        try {
            Product product = productRepository.getById(id);
            product.setStatus(false);
            saveOrUpdate(product);
        } catch (Exception e) {
            log.info("Delete fail");
        }
    }
}
