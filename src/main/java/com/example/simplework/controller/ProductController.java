package com.example.simplework.controller;

import com.example.simplework.entity.dto.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("${application-context-name}/v1")
public interface ProductController {

    @GetMapping("/get")
    ResponseEntity<Object> getProduct(@RequestParam Integer id, @RequestParam String name);

    @PostMapping("/create")
    ResponseEntity<Object> createProduct(@RequestBody ProductDTO product);

    @PutMapping("/product/{id}")
    ResponseEntity<Object> updateProduct(@PathVariable Integer id, @RequestBody ProductDTO product);

    @DeleteMapping("/product/{id}")
    ResponseEntity<Object> deleteProduct(@PathVariable Integer id);
}
