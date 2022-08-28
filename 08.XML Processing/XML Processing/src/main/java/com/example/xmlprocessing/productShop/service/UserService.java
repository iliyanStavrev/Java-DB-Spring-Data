package com.example.xmlprocessing.productShop.service;



import com.example.xmlprocessing.productShop.model.dto.out.query2.UsersWithSoldProductsRootDto;
import com.example.xmlprocessing.productShop.model.dto.out.query4.UsersAndProductsRootDto;
import com.example.xmlprocessing.productShop.model.entity.User;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface UserService {
    void seed() throws IOException, JAXBException;

    User getRandomUser();

    UsersWithSoldProductsRootDto findAllBySoldProductsSize();

    UsersAndProductsRootDto getAllUsersWithAtLeastOneProductSold();

}
