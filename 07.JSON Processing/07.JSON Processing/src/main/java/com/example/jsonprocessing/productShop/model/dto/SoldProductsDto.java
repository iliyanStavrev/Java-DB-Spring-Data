package com.example.jsonprocessing.productShop.model.dto;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class SoldProductsDto {

    @Expose
    private int count;
    @Expose
    private List<ProductsPriceNameDto> soldProducts;

    public SoldProductsDto() {
        this.soldProducts = new ArrayList<>();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ProductsPriceNameDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductsPriceNameDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
