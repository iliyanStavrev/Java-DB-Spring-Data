package com.example.jsonprocessing.carDealer.model.dto;

import com.example.jsonprocessing.carDealer.model.entity.Car;
import com.example.jsonprocessing.carDealer.model.entity.Supplier;
import com.google.gson.annotations.Expose;

import java.math.BigDecimal;
import java.util.List;

public class PartsSeedDto {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;
    @Expose
    private Integer quantity;
    @Expose
    private Supplier supplier;

    private List<Car> cars;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
