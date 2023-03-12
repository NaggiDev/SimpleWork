package com.example.simplework.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductDTO {
    int id;
    String productName;
    Double price;
    String url;
}
