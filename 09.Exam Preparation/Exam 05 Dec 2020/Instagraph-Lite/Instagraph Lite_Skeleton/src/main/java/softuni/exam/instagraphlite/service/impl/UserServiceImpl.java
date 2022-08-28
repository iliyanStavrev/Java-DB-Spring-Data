package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.UserSeedDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    private static final String PATH = "D:\\Работен плот\\JAVA DB SPRING\\" +
            "EXAM_PREPARATION\\05 Dec 2020\\Instagraph-Lite\\Instagraph Lite_Skeleton\\src\\" +
            "main\\resources\\files\\users.json";

    private final UserRepository userRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final PictureRepository pictureRepository;

    public UserServiceImpl(UserRepository userRepository, Gson gson,
                           ModelMapper modelMapper, ValidationUtil validationUtil, PictureRepository pictureRepository) {
        this.userRepository = userRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public boolean areImported() {
        return userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importUsers() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        UserSeedDto[] userSeeDtos = gson
                .fromJson(readFromFileContent(), UserSeedDto[].class);

        Arrays.stream(userSeeDtos)
                .filter(userSeeDto -> {
                    boolean isValid = validationUtil.isValid(userSeeDto);

                    Picture picture = pictureRepository
                            .findByPath(userSeeDto.getProfilePicture()).orElse(null);

                    if (picture == null){
                        stringBuilder.append("Invalid User")
                                .append(System.lineSeparator());
                        return false;
                    }

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported User: %s",
                                    userSeeDto.getUsername())
                                    : "Invalid User")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(userSeeDto -> {
                    User user = modelMapper.map(userSeeDto, User.class);

                    user.setProfilePicture(pictureRepository
                            .findByPath(userSeeDto.getProfilePicture()).orElse(null));
                    return user;
                })
                .forEach(userRepository::save);

        return stringBuilder.toString();
    }

    @Override
    public String exportUsersWithTheirPosts() {
        return null;
    }
}
