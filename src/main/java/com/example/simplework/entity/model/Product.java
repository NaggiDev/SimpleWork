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

    @Column(name = "MANUFACTURE_YEAR", nullable = false)
    private Date manufactureYear;

    @Column(name = "URL", length = 500)
    private String url;

    @Column(name = "PRODUCT_DESC", length = 500)
    private String productDesc;

    @Column(name = "STOCK", nullable = false)
    private Date stock;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Column(name = "STATUS")
    private Boolean status;

}
