package com.example.xmlprocessing;

import com.example.xmlprocessing.carDealer.service.*;
import com.example.xmlprocessing.productShop.service.CategoryService;
import com.example.xmlprocessing.productShop.service.ProductService;
import com.example.xmlprocessing.productShop.service.UserService;
import com.example.xmlprocessing.util.XmlParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {


    private static final String FILE_PATH_PRODUCT_SHOP = "src/main/resources/file/output/";
    private static final String FILE_PATH_CAR_DEALER = "src/main/resources/file/output/carDealer/";
    private static final String PRODUCTS_IN_RANGE = "products-in-range.xml";
    private static final String USERS_SOLD_PRODUCTS = "users-sold-products.xml";
    private static final String CATEGORIES_BY_PRODUCTS = "categories-by-products.xml";
    private static final String USERS_AND_PRODUCTS = "users-and-products.xml";
    private static final String CUSTOMERS_ORDERED = "ordered-customers.xml";
    private static final String CARS_FROM_MAKE = "toyota-cars.xml";
    private static final String SUPPLIERS_LOCAL = "local-suppliers.xml";
    private static final String CARS_AND_PARTS = "cars-and-parts.xml";
    private static final String CUSTOMERS_SALES = "customers-total-sales.xml";
    private static final String SALES_DISCOUNT = "sales-discounts.xml";

    Scanner scanner = new Scanner(System.in);

    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final XmlParser xmlParser;
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;

    public ConsoleRunner(CategoryService categoryService, UserService userService, ProductService productService, XmlParser xmlParser, SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.xmlParser = xmlParser;
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {

      // If you want to seed uncomment seedData() !!!
      //  seedData();

        System.out.println("Enter number exercise:");
        int number = Integer.parseInt(scanner.nextLine());

        switch (number){
            case 1:
                productsInRange();
                break;
            case 2:
                usersSoldProducts();
                break;
            case 3:
                categoriesByProducts();
                break;
            case 4:
                usersAndProducts();
                break;
            case 5:
                customersOrdered();
                break;
            case 6:
                carFromMake();
                break;
            case 7:
                suppliersLocal();
                break;
            case 8:
                carsAndParts();
                break;
            case 9:
                customersSales();
                break;
            case 10:
                salesWithDiscount();
                break;
        }

    }

    private void salesWithDiscount() throws JAXBException {
        xmlParser.writeToFile(FILE_PATH_CAR_DEALER + SALES_DISCOUNT,
                saleService.getSalesWithAppliedDiscount());
    }

    private void customersSales() throws JAXBException {
        xmlParser.writeToFile(FILE_PATH_CAR_DEALER + CUSTOMERS_SALES,
                saleService.getTotalSalesByCustomer());
    }

    private void carsAndParts() throws JAXBException {

        xmlParser.writeToFile(FILE_PATH_CAR_DEALER + CARS_AND_PARTS,
                carService.getAllCarsAndParts());
    }

    private void suppliersLocal() throws JAXBException {

        xmlParser.writeToFile(FILE_PATH_CAR_DEALER + SUPPLIERS_LOCAL,
                supplierService.getAllLocalSuppliers());
    }

    private void carFromMake() throws JAXBException {

        xmlParser.writeToFile(FILE_PATH_CAR_DEALER + CARS_FROM_MAKE,
                carService.getCarsFromMake("Toyota"));
    }

    private void customersOrdered() throws JAXBException {
        xmlParser.writeToFile(FILE_PATH_CAR_DEALER + CUSTOMERS_ORDERED,
                customerService.getAllCustomersOrdered());
    }

    private void usersAndProducts() throws JAXBException {

        xmlParser.writeToFile(FILE_PATH_PRODUCT_SHOP + USERS_AND_PRODUCTS,
                userService.getAllUsersWithAtLeastOneProductSold());
    }

    private void categoriesByProducts() throws JAXBException {

        xmlParser.writeToFile(FILE_PATH_PRODUCT_SHOP + CATEGORIES_BY_PRODUCTS,
                categoryService.getAllCategoriesByProductsCount());
    }

    private void usersSoldProducts() throws JAXBException {

        xmlParser.writeToFile(FILE_PATH_PRODUCT_SHOP + USERS_SOLD_PRODUCTS,
                userService.findAllBySoldProductsSize());
    }

    private void productsInRange() throws JAXBException {

        xmlParser.writeToFile(FILE_PATH_PRODUCT_SHOP + PRODUCTS_IN_RANGE,
                productService.findProductsInRange(BigDecimal.valueOf(500L),
                        BigDecimal.valueOf(1000L)));
    }

    private void seedData() throws IOException, JAXBException {

        categoryService.seed();
        userService.seed();
        productService.seed();
       supplierService.seed();
        partService.seed();
        carService.seed();
       customerService.seed();
        saleService.seed();

    }
}
