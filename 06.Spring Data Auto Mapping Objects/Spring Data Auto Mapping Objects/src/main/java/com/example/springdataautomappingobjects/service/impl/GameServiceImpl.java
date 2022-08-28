package com.example.springdataautomappingobjects.service.impl;

import com.example.springdataautomappingobjects.model.dto.GameAddDto;
import com.example.springdataautomappingobjects.model.dto.GameDetailDto;
import com.example.springdataautomappingobjects.model.entity.Game;
import com.example.springdataautomappingobjects.model.entity.User;
import com.example.springdataautomappingobjects.repository.GameRepository;
import com.example.springdataautomappingobjects.service.GameService;
import com.example.springdataautomappingobjects.service.UserService;
import com.example.springdataautomappingobjects.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {

    private final ValidationUtil validationUtil;
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;


    public GameServiceImpl(ValidationUtil validationUtil,
                           GameRepository gameRepository,
                           ModelMapper modelMapper, UserService userService) {
        this.validationUtil = validationUtil;
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public void addGame(GameAddDto gameAddDto) {

        if (!userService.isAdmin()){
            System.out.println("You have no permission to add a game!");
            return;
        }
            Set<ConstraintViolation<GameAddDto>> violations = validationUtil.violation(gameAddDto);

            if (!violations.isEmpty()) {
                violations.stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
                return;
            }

            Game game = modelMapper.map(gameAddDto, Game.class);

            gameRepository.save(game);
            System.out.println("Added " + gameAddDto.getTitle());

    }

    @Override
    public void editGame(String[] commands) {
        if (!userService.isAdmin()){
            System.out.println("You have no permission to add a game!");
            return;
        }
        Long id = Long.parseLong(commands[1]);

        Game game = gameRepository.findById(id).orElse(null);
        if (game == null) {
            System.out.printf("Game with id %d doesn't exist%n", id);
            return;
        }

        for (int i = 2; i < commands.length; i++) {
            String[] values = commands[i].split("=");

            switch (values[0]) {
                case "price":
                    game.setPrice(new BigDecimal(values[1]));
                    break;
                case "size":
                    game.setSize(Double.parseDouble(values[1]));
                    break;
                case "title":
                    game.setTitle(values[1]);
                    break;
                case "trailer":
                    game.setTrailer(values[1]);
                    break;
                case "description":
                    game.setDescription(values[1]);
                    break;
                case "releaseDate":
                    game.setReleaseDate(LocalDate.of(Integer.parseInt(values[1].split("-")[0]),
                            Integer.parseInt(values[1].split("-")[1]),
                            Integer.parseInt(values[1].split("-")[2])));
                    break;
                case "thumbnailURL":
                    game.setImageUrl(values[1]);
            }
        }
        System.out.println("Edited " + game.getTitle());
        gameRepository.save(game);

    }

    @Override
    public void deleteGame(Long id) {

        if (!userService.isAdmin()){
            System.out.println("You have no permission to add a game!");
            return;
        }

        Game game = gameRepository.findById(id).orElse(null);
        if (game == null) {
            System.out.printf("Game with id %d doesn't exist%n", id);
            return;
        }
        System.out.println("Deleted " + game.getTitle());
        gameRepository.delete(game);

    }

    @Override
    public void printAllGames() {
        gameRepository.findAll()
                .stream()
                .map(g -> String.format("%s %.2f",g.getTitle(),g.getPrice()))
                .forEach(System.out::println);
    }

    @Override
    public void detailGame(String title) {
        Game game = gameRepository.findByTitle(title);
        GameDetailDto gameDetailDto = modelMapper.map(game,GameDetailDto.class);

        System.out.println("Title: " + gameDetailDto.getTitle());
        System.out.println("Price: " + gameDetailDto.getPrice());
        System.out.println("Description: " + gameDetailDto.getDescription());
        System.out.println("Release Date: " + gameDetailDto.getReleaseDate());
    }
}
