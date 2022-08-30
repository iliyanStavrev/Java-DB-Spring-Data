package hiberspring.service.impl;

import hiberspring.model.dtos.ProductSeedRootDto;
import hiberspring.model.entities.Product;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.ProductRepository;
import hiberspring.service.ProductService;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String PATH = "D:\\Работен плот\\JAVA DB SPRING\\" +
            "EXAM_PREPARATION\\20 Dec 2018\\Hiberspring Inc._Skeleton\\HiberSpring\\skeleton\\src\\" +
            "main\\resources\\files\\products.xml";

    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public ProductServiceImpl(ProductRepository productRepository, BranchRepository branchRepository, ModelMapper modelMapper,
                              XmlParser xmlParser, ValidationUtil validationUtil) {
        this.productRepository = productRepository;
        this.branchRepository = branchRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean productsAreImported() {
        return productRepository.count() > 0;
    }

    @Override
    public String readProductsXmlFile() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importProducts() throws JAXBException, FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();

        ProductSeedRootDto productSeedRootDto = xmlParser
                .fromFile(PATH, ProductSeedRootDto.class);

        productSeedRootDto.getProduct()
                .stream()
                .filter(productSeedDto -> {
                    boolean isValid = validationUtil.isValid(productSeedDto);

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported Product %s.",
                                    productSeedDto.getName())
                                    : "Error: Invalid data.")
                            .append(System.lineSeparator());

                    return isValid;
                }).map(productSeedDto -> {
                    Product product = modelMapper.map(productSeedDto, Product.class);
                    product.setBranch(branchRepository
                            .findByName(productSeedDto.getBranch()));
                    return product;
                }).forEach(productRepository::save);


        return stringBuilder.toString();
    }
}
