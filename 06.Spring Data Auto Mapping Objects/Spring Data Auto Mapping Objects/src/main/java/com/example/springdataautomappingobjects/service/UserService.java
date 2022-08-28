package com.example.springdataautomappingobjects.service;

import com.example.springdataautomappingobjects.model.dto.UserLoginDto;
import com.example.springdataautomappingobjects.model.dto.UserRegisterDto;
import com.example.springdataautomappingobjects.model.entity.User;

public interface UserService {

    public void registerUser(UserRegisterDto userRegisterDto);

    public void loginUser(UserLoginDto userLoginDto);

    void logout();

    boolean hasNoLoggedUser();

    boolean isAdmin();

    void getOwnGames();

    void buyGame(String title);
}
