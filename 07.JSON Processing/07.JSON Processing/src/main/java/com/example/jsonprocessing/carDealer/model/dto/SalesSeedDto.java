package com.example.jsonprocessing.carDealer.model.dto;

import com.example.jsonprocessing.carDealer.model.entity.Car;
import com.example.jsonprocessing.carDealer.model.entity.Customer;
import com.google.gson.annotations.Expose;

public class SalesSeedDto {

    @Expose
    private Double discount;
    @Expose
    private Car car;
    @Expose
    private Customer customer;

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
