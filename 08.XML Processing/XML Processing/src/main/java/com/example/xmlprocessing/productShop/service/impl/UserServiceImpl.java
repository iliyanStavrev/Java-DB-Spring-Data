package com.example.xmlprocessing.productShop.service.impl;


import com.example.xmlprocessing.productShop.model.dto.out.query2.ProductsNamePriceBuyerDto;
import com.example.xmlprocessing.productShop.model.dto.out.query2.ProductsRootDto;
import com.example.xmlprocessing.productShop.model.dto.out.query2.UsersWithSoldProductsDto;
import com.example.xmlprocessing.productShop.model.dto.out.query2.UsersWithSoldProductsRootDto;
import com.example.xmlprocessing.productShop.model.dto.out.query4.ProductNamePriceDto;
import com.example.xmlprocessing.productShop.model.dto.out.query4.SoldProductCountDto;
import com.example.xmlprocessing.productShop.model.dto.out.query4.UserNameAndAgeDto;
import com.example.xmlprocessing.productShop.model.dto.out.query4.UsersAndProductsRootDto;
import com.example.xmlprocessing.productShop.model.dto.seed.UserSeedRootDto;
import com.example.xmlprocessing.productShop.model.entity.User;
import com.example.xmlprocessing.productShop.repository.UserRepository;
import com.example.xmlprocessing.productShop.service.UserService;
import com.example.xmlprocessing.util.ValidationUtil;
import com.example.xmlprocessing.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final String FILE_PATH =
            "src/main/resources/file/data/users.xml";


    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(XmlParser xmlParser, ValidationUtil validationUtil, UserRepository userRepository, ModelMapper modelMapper) {
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seed() throws IOException, JAXBException {

        if (userRepository.count() > 0) {
            return;
        }

        UserSeedRootDto userSeedRootDto =
                xmlParser.fromFile(FILE_PATH, UserSeedRootDto.class);

        userSeedRootDto.getUsers()
                .stream()
                .filter(validationUtil::isValid)
                .map(userSeedDto ->
                        modelMapper.map(userSeedDto, User.class))
                .forEach(userRepository::save);

    }

    @Override
    public User getRandomUser() {

        return userRepository.findById(ThreadLocalRandom
                        .current().nextLong(1, userRepository.count()) + 1)
                .orElse(null);
    }

    @Override
    public UsersWithSoldProductsRootDto findAllBySoldProductsSize() {


        List<UsersWithSoldProductsDto> users = userRepository.findAllBySoldProductsSize()
                .stream()
                .map(user -> {
                    UsersWithSoldProductsDto usersWithSoldProductsDto =
                            modelMapper.map(user, UsersWithSoldProductsDto.class);

                    ProductsRootDto productsRootDto = new ProductsRootDto();
                    productsRootDto.setProducts(user.getSoldProducts()
                            .stream()
                            .map(sale -> {

                                ProductsNamePriceBuyerDto productsNamePriceBuyerDto =
                                        modelMapper.map(sale, ProductsNamePriceBuyerDto.class);
                                if (sale.getBuyer() == null) {
                                    return productsNamePriceBuyerDto;
                                }
                                if (sale.getBuyer().getFirstName() == null) {
                                    productsNamePriceBuyerDto.setFirstName("null");
                                } else {
                                    productsNamePriceBuyerDto.setFirstName(sale.getBuyer().getFirstName());
                                }
                                productsNamePriceBuyerDto.setLastName(sale.getBuyer().getLastName());
                                return productsNamePriceBuyerDto;
                            })
                            .collect(Collectors.toList()));
                    List<ProductsRootDto> products = new ArrayList<>();
                    products.add(productsRootDto);
                    usersWithSoldProductsDto.setProducts(products);
                    return usersWithSoldProductsDto;
                })
                .collect(Collectors.toList());
        UsersWithSoldProductsRootDto usersWithSoldProductsRootDto =
                new UsersWithSoldProductsRootDto();

        usersWithSoldProductsRootDto.setUsers(users);

        return usersWithSoldProductsRootDto;
    }

    @Override
    public UsersAndProductsRootDto getAllUsersWithAtLeastOneProductSold() {

        UsersAndProductsRootDto usersAndProductsRootDto =
                new UsersAndProductsRootDto();

        usersAndProductsRootDto.setCount(userRepository
                .getAllUsersWithAtLeastOneProductSold().size());
        List<UserNameAndAgeDto> userNameAndAgeDtos =
                userRepository.getAllUsersWithAtLeastOneProductSold()
                        .stream()
                        .map(user -> {
                            UserNameAndAgeDto userNameAndAgeDto =
                                    modelMapper.map(user, UserNameAndAgeDto.class);

                            SoldProductCountDto soldProductCountDto =
                                    new SoldProductCountDto();

                            soldProductCountDto.setProducts(user.getSoldProducts()
                                    .stream()
                                    .filter(sale -> sale.getBuyer() != null)
                                    .map(sale -> modelMapper.map(sale, ProductNamePriceDto.class))
                                    .collect(Collectors.toList()));

                            soldProductCountDto.setCount(soldProductCountDto.getProducts().size());

                            userNameAndAgeDto.setProducts(soldProductCountDto);
                            return userNameAndAgeDto;
                        })
                        .sorted((u1, u2) -> u2.getProducts().getCount() - u1.getProducts().getCount())
                        .toList();

        usersAndProductsRootDto.setUsers(userNameAndAgeDtos);

        return usersAndProductsRootDto;
    }
}
