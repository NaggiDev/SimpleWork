package com.example.simplework.entity.model;

import lombok.Data;

@Data
public class Paging {

    private int size;
    private long totalElement;
    private int totalPage;
    private int currentPage;
    private String nextPageURL;
    private String previousPageURL;

    public Paging(int size, long totalElement, int totalPage, int currentPage) {
        this.size = size;
        this.totalElement = totalElement;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
    }
}
