package com.example.jsonprocessing.productShop.service;

import com.example.jsonprocessing.productShop.model.dto.UserWithSoldProductsDto;
import com.example.jsonprocessing.productShop.model.dto.UsersWithSalesListDto;
import com.example.jsonprocessing.productShop.model.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void seed() throws IOException;

    User getRandomUser();

    List<UserWithSoldProductsDto> findAllBySoldProductsSize();

    UsersWithSalesListDto getSellsByUser();
}
