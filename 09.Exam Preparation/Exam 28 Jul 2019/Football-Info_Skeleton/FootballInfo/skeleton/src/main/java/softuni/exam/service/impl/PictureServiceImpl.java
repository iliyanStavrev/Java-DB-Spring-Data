package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.model.dto.PictureSeedRootDto;
import softuni.exam.model.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PictureServiceImpl implements PictureService {

    private static final String PATH = "D:\\Работен плот\\JAVA DB SPRING\\" +
            "EXAM_PREPARATION\\28 Jul 2019\\Football-Info_Skeleton\\FootballInfo\\skeleton\\src\\" +
            "main\\resources\\files\\xml\\pictures.xml";

    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper,
                              XmlParser xmlParser, ValidationUtil validationUtil) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {

        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importPictures() throws JAXBException, FileNotFoundException {

        StringBuilder stringBuilder = new StringBuilder();

        PictureSeedRootDto pictureSeedRootDto = xmlParser
                .fromFile(PATH, PictureSeedRootDto.class);

        pictureSeedRootDto.getPicture()
                .stream()
                .filter(pictureSeedDto -> {
                    boolean isValid = validationUtil.isValid(pictureSeedDto);

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported picture - %s",
                                    pictureSeedDto.getUrl())
                                    : "Invalid Picture")
                            .append(System.lineSeparator());

                    return isValid;
                }).map(pictureSeedDto -> modelMapper
                        .map(pictureSeedDto, Picture.class))
                .forEach(pictureRepository::save);

        return stringBuilder.toString();
    }


}
