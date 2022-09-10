package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AgentSeedDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class AgentServiceImpl implements AgentService {

    private static final String PATH = "D:\\Работен плот\\JAVA DB SPRING\\" +
            "EXAM_PREPARATION\\EXAM\\Real Estate Agency_Skeleton\\skeleton\\src\\" +
            "main\\resources\\files\\json\\agents.json";

    private final AgentRepository agentRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public AgentServiceImpl(AgentRepository agentRepository, TownRepository townRepository, ModelMapper modelMapper,
                            Gson gson, ValidationUtil validationUtil) {
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importAgents() throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        AgentSeedDto[] agentSeedDtos = gson
                .fromJson(readAgentsFromFile(), AgentSeedDto[].class);

        Arrays.stream(agentSeedDtos)
                .filter(agentSeedDto -> {
                    boolean isValid = validationUtil.isValid(agentSeedDto);

                    Agent agent = agentRepository
                            .findByFirstName(agentSeedDto.getFirstName());

                    if (agent != null) {
                        stringBuilder.append("Invalid agent")
                                .append(System.lineSeparator());
                        return false;
                    }

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported agent - %s %s",
                                    agentSeedDto.getFirstName(),
                                    agentSeedDto.getLastName())
                                    : "Invalid agent")
                            .append(System.lineSeparator());

                    return isValid;
                }).map(agentSeedDto -> {
                    Agent agent = modelMapper.map(agentSeedDto, Agent.class);

                    agent.setTown(townRepository
                            .findByTownName(agentSeedDto.getTown()));

                    return agent;
                }).forEach(agentRepository::save);

        return stringBuilder.toString();
    }
}
