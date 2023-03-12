package com.example.simplework.repository;

import com.example.simplework.entity.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> getAllById(Integer id);

    List<Product> getAllByProductName(String productName);

}
