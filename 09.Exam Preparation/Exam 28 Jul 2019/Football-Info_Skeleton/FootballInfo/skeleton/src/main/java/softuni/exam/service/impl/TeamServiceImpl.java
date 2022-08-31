package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.model.dto.TeamSeedRootDto;
import softuni.exam.model.entities.Picture;
import softuni.exam.model.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.service.TeamService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TeamServiceImpl implements TeamService {

    private static final String PATH = "D:\\Работен плот\\JAVA DB SPRING\\EXAM_PREPARATION" +
            "\\28 Jul 2019\\Football-Info_Skeleton\\FootballInfo\\skeleton\\src\\" +
            "main\\resources\\files\\xml\\teams.xml";

    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public TeamServiceImpl(TeamRepository teamRepository, PictureRepository pictureRepository, ModelMapper modelMapper,
                           XmlParser xmlParser, ValidationUtil validationUtil) {
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importTeams() throws JAXBException, FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();

        TeamSeedRootDto teamSeedRootDto = xmlParser
                .fromFile(PATH, TeamSeedRootDto.class);

        teamSeedRootDto.getTeam()
                .stream()
                .filter(teamSeedDto -> {
                    boolean isValid = validationUtil.isValid(teamSeedDto);

                    Picture picture = pictureRepository
                            .findByUrl(teamSeedDto.getPicture().getUrl());

                    if (picture == null) {
                        stringBuilder.append("Invalid Team")
                                .append(System.lineSeparator());
                        return false;
                    }


                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported - %s",
                                    teamSeedDto.getName())
                                    : "Invalid Team")
                            .append(System.lineSeparator());

                    return isValid;
                }).map(teamSeedDto -> {
                    Team team = modelMapper.map(teamSeedDto, Team.class);
                    team.setPicture(pictureRepository
                            .findByUrl(teamSeedDto.getPicture().getUrl()));

                    return team;
                }).forEach(teamRepository::save);

        return stringBuilder.toString();
    }


}
