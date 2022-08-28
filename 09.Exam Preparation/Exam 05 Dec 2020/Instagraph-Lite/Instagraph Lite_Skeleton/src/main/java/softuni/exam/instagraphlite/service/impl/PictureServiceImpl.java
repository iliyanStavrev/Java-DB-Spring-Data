package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.PictureSeedDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class PictureServiceImpl implements PictureService {

    private static final String PATH = "D:\\Работен плот\\JAVA DB SPRING\\EXAM_PREPARATION\\05 Dec 2020\\" +
            "Instagraph-Lite\\Instagraph Lite_Skeleton\\" +
            "src\\main\\resources\\files\\pictures.json";

    private final PictureRepository pictureRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public PictureServiceImpl(PictureRepository pictureRepository, Gson gson,
                              ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.pictureRepository = pictureRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importPictures() throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        PictureSeedDto[] pictureSeedDtos = gson
                .fromJson(readFromFileContent(), PictureSeedDto[].class);

        Arrays.stream(pictureSeedDtos)
                .filter(pictureSeedDto -> {
                    boolean isValid = validationUtil.isValid(pictureSeedDto);

                    Optional<Picture> picture = pictureRepository
                            .findByPath(pictureSeedDto.getPath());

                    if (picture.isPresent()){
                        stringBuilder.append("Invalid Picture");
                        return false;
                    }

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported Picture, with size %.2f",
                                    pictureSeedDto.getSize())
                                    : "Invalid Picture")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(pictureSeedDto -> modelMapper
                        .map(pictureSeedDto, Picture.class))
                .forEach(pictureRepository::save);

        return stringBuilder.toString();
    }

    @Override
    public String exportPictures() {
        return null;
    }
}
