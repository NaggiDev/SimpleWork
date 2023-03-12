package com.example.simplework.entity.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product implements Serializable {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "PRODUCT_NAME", nullable = false, length = 20)
    private String productName;

    @Column(name = "PRICE", nullable = false)
    private Double price;

    @Column(name = "YEAR", nullable = false)
    private Date year;

    @Column(name = "URL", length = 500)
    private String url;

}