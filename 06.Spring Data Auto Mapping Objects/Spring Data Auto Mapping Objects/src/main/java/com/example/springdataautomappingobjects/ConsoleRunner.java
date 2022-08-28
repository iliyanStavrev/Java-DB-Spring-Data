package com.example.springdataautomappingobjects;

import com.example.springdataautomappingobjects.model.dto.GameAddDto;
import com.example.springdataautomappingobjects.model.dto.UserLoginDto;
import com.example.springdataautomappingobjects.model.dto.UserRegisterDto;
import com.example.springdataautomappingobjects.service.GameService;
import com.example.springdataautomappingobjects.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Service
public class ConsoleRunner implements CommandLineRunner {

    Scanner scanner = new Scanner(System.in);

    private final UserService userService;
    private final GameService gameService;

    public ConsoleRunner(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {

        while (true) {

            System.out.println("Enter your commands:");
            String[] commands = scanner.nextLine().split("\\|");

            switch (commands[0]) {

                case "RegisterUser":
                    userService.registerUser(new UserRegisterDto(
                            commands[1], commands[2],
                            commands[3], commands[4]));
                    break;
                case "LoginUser":
                    userService.loginUser(new UserLoginDto(commands[1],commands[2]));
                    break;
                case "Logout":
                    userService.logout();
                case "AddGame":
                    gameService.addGame(new GameAddDto(commands[1],new BigDecimal(commands[2]),
                            Double.parseDouble(commands[3]),
                            commands[4],commands[5],commands[6], LocalDate.parse(commands[7],
                            DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
                    break;
                case "EditGame":
                    gameService.editGame(commands);
                    break;
                case "DeleteGame":
                    gameService.deleteGame(Long.parseLong(commands[1]));
                    break;
                case "AllGames":
                    gameService.printAllGames();
                    break;
                case "DetailGame":
                    gameService.detailGame(commands[1]);
                    break;
                case "OwnedGames":
                    userService.getOwnGames();
                    break;
            }
        }
    }
}
