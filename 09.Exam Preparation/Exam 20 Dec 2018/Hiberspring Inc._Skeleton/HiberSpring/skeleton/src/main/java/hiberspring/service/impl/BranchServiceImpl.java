package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.model.dtos.BranchSeedDto;
import hiberspring.model.entities.Branch;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.TownRepository;
import hiberspring.service.BranchService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class BranchServiceImpl implements BranchService {

    private static final String PATH = "D:\\Работен плот\\JAVA DB SPRING\\EXAM_PREPARATION\\20 Dec 2018" +
            "\\Hiberspring Inc._Skeleton\\HiberSpring\\skeleton\\src\\main\\" +
            "resources\\files\\branches.json";

    private final TownRepository townRepository;
    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public BranchServiceImpl(TownRepository townRepository, BranchRepository branchRepository,
                             ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.branchRepository = branchRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean branchesAreImported() {
        return branchRepository.count() > 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importBranches(String branchesFileContent) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        BranchSeedDto[] branchSeedDtos = gson
                .fromJson(readBranchesJsonFile(), BranchSeedDto[].class);

        Arrays.stream(branchSeedDtos)
                .filter(branchSeedDto -> {
                    boolean isValid = validationUtil.isValid(branchSeedDto);

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported Branch %s.",
                                    branchSeedDto.getName())
                                    : "Error: Invalid data.")
                            .append(System.lineSeparator());

                    return isValid;
                }).map(branchSeedDto -> {
                    Branch branch = modelMapper.map(branchSeedDto, Branch.class);
                    branch.setTown(townRepository
                            .findByName(branchSeedDto.getTown()));
                    return branch;
                }).forEach(branchRepository::save);

        return stringBuilder.toString();
    }
}
