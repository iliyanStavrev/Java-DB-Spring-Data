package hiberspring.service.impl;

import hiberspring.model.dtos.EmployeeSeedRootDto;
import hiberspring.model.entities.Employee;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.repository.EmployeeRepository;
import hiberspring.service.EmployeeService;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String PATH = "D:\\Работен плот\\JAVA DB SPRING\\" +
            "EXAM_PREPARATION\\20 Dec 2018\\Hiberspring Inc._Skeleton\\HiberSpring\\skeleton\\src\\main\\" +
            "resources\\files\\employees.xml";

    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final EmployeeCardRepository employeeCardRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               BranchRepository branchRepository,
                               EmployeeCardRepository employeeCardRepository,
                               ModelMapper modelMapper, XmlParser xmlParser,
                               ValidationUtil validationUtil) {
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
        this.employeeCardRepository = employeeCardRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean employeesAreImported() {
        return employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importEmployees() throws JAXBException, FileNotFoundException {

        StringBuilder stringBuilder = new StringBuilder();

        EmployeeSeedRootDto employeeSeedRootDto = xmlParser
                .fromFile(PATH, EmployeeSeedRootDto.class);

        employeeSeedRootDto.getEmployee()
                .stream()
                .filter(employeeSeedDto -> {
                    boolean isValid = validationUtil.isValid(employeeSeedDto);

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported Employee %s %s.",
                                    employeeSeedDto.getFirstName(),
                                    employeeSeedDto.getLastName())
                                    : "Error: Invalid data.")
                            .append(System.lineSeparator());

                    return isValid;
                }).map(employeeSeedDto -> {
                    Employee employee = modelMapper
                            .map(employeeSeedDto, Employee.class);
                    employee.setBranch(branchRepository
                            .findByName(employeeSeedDto.getBranch()));
                    employee.setCard(employeeCardRepository
                            .findByNumber(employeeSeedDto.getCard()));
                    return employee;
                }).forEach(employeeRepository::save);

        return stringBuilder.toString();
    }

    @Override
    public String exportProductiveEmployees() {
        return employeeRepository
                .findAllProductiveEmployees()
                .stream()
                .map(employee -> String.format(
                                "Name: %s %s\n" +
                                "Position: %s\n" +
                                "Card Number: %s\n" +
                                "----------------\n",
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getPosition(),
                        employee.getCard().getNumber()))
                .collect(Collectors.joining(""));
    }
}
