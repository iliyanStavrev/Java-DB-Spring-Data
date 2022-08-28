package com.example.springdataautomappingobjects.service.impl;

import com.example.springdataautomappingobjects.model.dto.UserLoginDto;
import com.example.springdataautomappingobjects.model.dto.UserRegisterDto;
import com.example.springdataautomappingobjects.model.entity.Game;
import com.example.springdataautomappingobjects.model.entity.User;
import com.example.springdataautomappingobjects.repository.GameRepository;
import com.example.springdataautomappingobjects.repository.UserRepository;
import com.example.springdataautomappingobjects.service.UserService;
import com.example.springdataautomappingobjects.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private User loggedInUser;
    private final GameRepository gameRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gameRepository = gameRepository;
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPass())) {
            System.out.println("Passwords don't match!");
            return;
        }

        Set<ConstraintViolation<UserRegisterDto>> violations = validationUtil.violation(userRegisterDto);
        if (!violations.isEmpty()) {
            violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }


        User user = modelMapper.map(userRegisterDto, User.class);
        if (userRepository.count() == 0) {
            user.setAdmin(true);
        } else {
            user.setAdmin(false);
        }
        userRepository.save(user);
        System.out.printf("%s was registered%n", user.getFullName());

    }

    @Override
    public void loginUser(UserLoginDto userLoginDto) {

        Set<ConstraintViolation<UserLoginDto>> violations = validationUtil
                .violation(userLoginDto);
        if (!violations.isEmpty()) {
            violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        User user = userRepository
                .findByEmailAndPassword(userLoginDto.getEmail()
                        , userLoginDto.getPassword())
                .orElse(null);

        if (user == null) {
            System.out.println("Incorrect username / password");
            return;
        }

        loggedInUser = user;
        System.out.printf("Successfully logged in %s%n", loggedInUser.getFullName());
    }

    @Override
    public void logout() {
        if (loggedInUser == null) {
            System.out.println("Cannot log out. No user was logged in.");
        } else {
            System.out.printf("User %s successfully logged out%n", loggedInUser.getFullName());
            loggedInUser = null;
        }
    }

    @Override
    public boolean hasNoLoggedUser() {
        return loggedInUser == null;
    }

    @Override
    public boolean isAdmin() {
        if (loggedInUser == null) {
            return false;
        }
        return loggedInUser.getAdmin();
    }

    @Override
    public void getOwnGames() {
        if (loggedInUser == null) {
            System.out.println("No user is currently logged in!");
            return;
        }
        buyGame("Bubble");
        buyGame("Panga");

        Set<Game> games = loggedInUser.getGames();
                 games.stream()
                .map(g -> String.format("%s", g.getTitle()))
                .forEach(System.out::println);
    }

    @Override
    public void buyGame(String title) {
        Game game = gameRepository.findByTitle(title);
        loggedInUser.getGames().add(game);

    }
}
