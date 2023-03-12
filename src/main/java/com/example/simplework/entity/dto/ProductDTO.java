package com.example.simplework.entity.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private int id;
    private String productName;
    private Double price;
    private String url;
}
