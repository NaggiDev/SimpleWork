package com.example.simplework.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductDTO {
    private int id;
    private String productName;
    private Double price;
    private String url;
    private Date manufactureYear;
}
