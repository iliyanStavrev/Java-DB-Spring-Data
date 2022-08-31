package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.model.dto.PlayerSeedDto;
import softuni.exam.model.entities.Player;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.service.PlayerService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final String PATH = "D:\\Работен плот\\JAVA DB SPRING\\" +
            "EXAM_PREPARATION\\28 Jul 2019\\Football-Info_Skeleton\\FootballInfo\\skeleton\\src\\" +
            "main\\resources\\files\\json\\players.json";

    private final PictureRepository pictureRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public PlayerServiceImpl(PictureRepository pictureRepository,
                             TeamRepository teamRepository, PlayerRepository playerRepository,
                             ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.pictureRepository = pictureRepository;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return Files.readString(Path.of(PATH));
    }


    @Override
    public String importPlayers() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        PlayerSeedDto[] playerSeedDtos = gson
                .fromJson(readPlayersJsonFile(), PlayerSeedDto[].class);

        Arrays.stream(playerSeedDtos)
                .filter(playerSeedDto -> {
                    boolean isValid = validationUtil.isValid(playerSeedDto);

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported player: %s %s",
                                    playerSeedDto.getFirstName(),
                                    playerSeedDto.getLastName())
                                    : "Invalid Player")
                            .append(System.lineSeparator());


                    return isValid;
                }).map(playerSeedDto -> {
                    Player player = modelMapper.map(playerSeedDto, Player.class);
                    player.setPicture(pictureRepository
                            .findByUrl(playerSeedDto.getPicture().getUrl()));
                    player.setTeam(teamRepository
                            .findByName(playerSeedDto.getTeam().getName()));

                    return player;
                }).forEach(playerRepository::save);

        return stringBuilder.toString();
    }


    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        return playerRepository
                .findAllBySalaryGreaterThanOrderBySalaryDesc(BigDecimal.valueOf(100000))
                .stream()
                .map(player -> String.format("Player name: %s %s\n" +
                                "\tNumber: %d\n" +
                                "\tSalary: %f\n" +
                                "\tTeam: %s\n", player.getFirstName(),
                        player.getLastName(), player.getNumber(),
                        player.getSalary(),
                        player.getTeam().getName()))
                .collect(Collectors.joining(""));
    }

    @Override
    public String exportPlayersInATeam() {

        return playerRepository
                .findAllByTeamNameOrderById("North Hub")
                .stream()
                .map(player -> String.format("Team: %s\n" +
                                "\tPlayer name: %s %s - %s\n" +
                                "\tNumber: %d\n", player.getTeam().getName(),
                        player.getFirstName(),
                        player.getLastName(),
                        player.getPosition(),
                        player.getNumber()
                ))
                .collect(Collectors.joining(""));
    }
}
