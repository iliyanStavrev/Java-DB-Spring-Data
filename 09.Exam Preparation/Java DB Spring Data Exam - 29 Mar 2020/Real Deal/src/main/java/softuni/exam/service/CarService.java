package softuni.exam.service;



import softuni.exam.models.dto.CarExportDto;
import softuni.exam.models.entity.Car;

import java.io.IOException;
import java.util.List;

//ToDo - Before start App implement this Service and set areImported to return false
public interface CarService {

    boolean areImported();

    String readCarsFileContent() throws IOException;
	
	String importCars() throws IOException;

    String getCarsOrderByPicturesCountThenByMake();

    List<CarExportDto> getAllCars();
}