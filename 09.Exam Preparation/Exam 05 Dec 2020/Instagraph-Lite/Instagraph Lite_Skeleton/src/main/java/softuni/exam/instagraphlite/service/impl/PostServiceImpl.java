package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.PostSeedRootDto;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.models.entity.Post;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.util.ValidationUtil;
import softuni.exam.instagraphlite.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PostServiceImpl implements PostService {

    private static final String PATH = "D:\\Работен плот\\JAVA DB SPRING\\" +
            "EXAM_PREPARATION\\05 Dec 2020\\Instagraph-Lite\\Instagraph Lite_Skeleton\\" +
            "src\\main\\resources\\files\\posts.xml";

    private final PostRepository postRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final PictureRepository pictureRepository;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, XmlParser xmlParser,
                           ModelMapper modelMapper, ValidationUtil validationUtil, PictureRepository pictureRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.pictureRepository = pictureRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean areImported() {
        return postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException{
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importPosts() throws IOException, JAXBException {

        StringBuilder stringBuilder = new StringBuilder();

        PostSeedRootDto postSeedRootDto = xmlParser
                .fromFile(PATH, PostSeedRootDto.class);

        postSeedRootDto.getPost()
                .stream()
                .filter(postSeedDto -> {
                    boolean isValid = validationUtil.isValid(postSeedDto);
                    Picture picture = pictureRepository
                            .findByPath(postSeedDto.getPicture().getPath()).orElse(null);

                    if (picture == null){
                        stringBuilder.append("Invalid Post")
                                .append(System.lineSeparator());
                        return false;
                    }

                    User user = userRepository.findByUsername(postSeedDto
                            .getUser().getUsername()).orElse(null);
                    if (user == null){
                        stringBuilder.append("Invalid Post")
                                .append(System.lineSeparator());
                        return false;
                    }
                    return isValid;
                })
                .map(postSeedDto -> {
                    Post post = modelMapper
                            .map(postSeedDto,Post.class);

                    post.setPicture(pictureRepository
                            .findByPath(postSeedDto.getPicture().getPath()).orElse(null));
                    post.setUser(userRepository
                            .findByUsername(postSeedDto.getUser().getUsername()).orElse(null));

                    return post;
                })
                .forEach(postRepository::save);
        return stringBuilder.toString();
    }
}
