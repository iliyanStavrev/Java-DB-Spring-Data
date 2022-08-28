package com.example.jsonprocessing.productShop.service.impl;


import com.example.jsonprocessing.productShop.model.dto.*;
import com.example.jsonprocessing.productShop.model.entity.User;
import com.example.jsonprocessing.productShop.repository.UserRepository;
import com.example.jsonprocessing.productShop.service.UserService;
import com.example.jsonprocessing.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final String PATH = "src/main/resources/files/data/users.json";

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(ModelMapper modelMapper,
                           UserRepository userRepository,
                           Gson gson, ValidationUtil validationUtil) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seed() throws IOException {
        if (userRepository.count() > 0){
            return;
        }

        Arrays.stream(gson.fromJson(
                Files.readString(Path.of(PATH)), UserSeedDto[].class))
                .filter(validationUtil::isValid)
                .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                .forEach(userRepository::save);
    }

    @Override
    public User getRandomUser() {

        long id = ThreadLocalRandom.current()
                .nextLong(1, userRepository.count() + 1);

        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserWithSoldProductsDto> findAllBySoldProductsSize() {

       return this.userRepository
                .findAllBySoldProductsSize()
                .stream()
                .map(user -> modelMapper.map(user, UserWithSoldProductsDto.class)).
                collect(Collectors.toList());

    }

    @Override
    public UsersWithSalesListDto getSellsByUser() {
        List<UsersFirstAndLastNameDto> users = this.userRepository.getAllUsersWithAtLeastOneProductSold()
                .stream()
                .map(user -> {
                    UsersFirstAndLastNameDto userDto = modelMapper
                            .map(user, UsersFirstAndLastNameDto.class);
                    SoldProductsDto soldProductsDto = new SoldProductsDto();

                    soldProductsDto.setSoldProducts(user.getSoldProducts()
                            .stream()
                            .filter(sale -> sale.getBuyer() != null)
                            .map(sale -> modelMapper.map(sale, ProductsPriceNameDto.class))
                            .collect(Collectors.toList()));
                    soldProductsDto.setCount(soldProductsDto.getSoldProducts().size());
                    userDto.setSoldProducts(soldProductsDto);
                    return userDto;
                })
                .sorted((u1,u2) -> u2.getSoldProducts().getCount() - u1.getSoldProducts().getCount())
                .collect(Collectors.toList());

        UsersWithSalesListDto usersWithSalesListDto = new UsersWithSalesListDto();
        usersWithSalesListDto.setUsers(users);
        usersWithSalesListDto.setCount(users.size());

        return usersWithSalesListDto;
    }

}
