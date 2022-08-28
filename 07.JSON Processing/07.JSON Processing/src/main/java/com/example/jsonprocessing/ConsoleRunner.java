package com.example.jsonprocessing;

import com.example.jsonprocessing.carDealer.service.*;
import com.example.jsonprocessing.productShop.model.dto.ProductNameAndPriceAndSellerDto;
import com.example.jsonprocessing.productShop.model.dto.UserWithSoldProductsDto;
import com.example.jsonprocessing.productShop.service.CategoryService;
import com.example.jsonprocessing.productShop.service.ProductService;
import com.example.jsonprocessing.productShop.service.UserService;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    Scanner scanner = new Scanner(System.in);
    private static final String PATH = "src/main/resources/files/output/";
    private static final String PRODUCT_IN_RANGE_PATH = "products-in-range.json";
    private static final String USERS_SOLD_PRODUCTS = "users-sold-products.json";
    private static final String CATEGORY_BY_PRODUCTS = "categories-by-products.json";
    private static final String USERS_AND_PRODUCTS = "users-and-products.json";
    private static final String ORDERED_CUSTOMERS = "ordered-customers.json";
    private static final String TOYOTA_CARS = "toyota-cars.json";
    private static final String LOCAL_SUPPLIERS = "local-suppliers.json";
    private static final String CARS_AND_PARTS = "cars-and-parts.json";
    private static final String CUSTOMERS_TOTAL_SALES = "customers-total-sales.json";
    private static final String SALES_DISCOUNTS = "sales-discounts.json";

    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final Gson gson;
    private final SupplierService supplierService;
    private final CustomerService customerService;
    private final PartService partService;
    private final CarService carService;
    private final SaleService saleService;

    public ConsoleRunner(CategoryService categoryService, UserService userService,
                         ProductService productService, Gson gson,
                         SupplierService supplierService, CustomerService customerService,
                         PartService partService, CarService carService, SaleService saleService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.gson = gson;
        this.supplierService = supplierService;
        this.customerService = customerService;
        this.partService = partService;
        this.carService = carService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {
        // If you want to seed Data uncomment seedData() !!!
        seedData();
        System.out.println("Enter exercise number:");
        int number = Integer.parseInt(scanner.nextLine());

        switch (number){
            case 1:
                productsInRange();
                break;
            case 2:
                usersSoldProducts();
                break;
            case 3:
                categoriesByProductsCount();
                break;
            case 4:
                usersAndProducts();
                break;
                // Car Dealer
            case 5: // Query 1 – Ordered Customers
                orderedCustomers();
                break;
            case 6: // Query 2 – Cars from Make Toyota
                carsFromMake();
                break;
            case 7: // Query 3 – Local Suppliers
                localSuppliers();
                break;
            case 8: // Query 4 – Cars with Their List of Parts
                carsAndParts();
                break;
            case 9:// Query 5 – Total Sales by Customer
                customersTotalSales();
                break;
            case 10: // Query 6 – Sales with Applied Discount
                salesWithAppliedDiscount();
                break;
        }

    }

    private void salesWithAppliedDiscount() throws IOException {
        String content = gson.toJson(saleService.getSalesWithAppliedDiscount());

        writeToFile(PATH + SALES_DISCOUNTS, content);
    }

    private void customersTotalSales() throws IOException {
        String content = gson.toJson(saleService.getTotalSalesByCustomer());

        writeToFile(PATH + CUSTOMERS_TOTAL_SALES, content);
    }

    private void carsAndParts() throws IOException {
        String content = gson.toJson(carService.getAllCarsAndParts());

        writeToFile(PATH + CARS_AND_PARTS, content);


    }

    private void localSuppliers() throws IOException {

        String content = gson.toJson(supplierService.getAllLocalSuppliers());

        writeToFile(PATH + LOCAL_SUPPLIERS, content);
    }

    private void carsFromMake() throws IOException {
        String content = gson.toJson(carService.findAllByMake("Toyota"));

        writeToFile(PATH + TOYOTA_CARS,content);
    }

    private void orderedCustomers() throws IOException {
        String content = gson.toJson(customerService.findAllByBirthDate());

        writeToFile(PATH + ORDERED_CUSTOMERS, content);
    }

    private void usersAndProducts() throws IOException {
        String content = gson.toJson(userService.getSellsByUser());

        writeToFile(PATH + USERS_AND_PRODUCTS, content);

    }

    private void categoriesByProductsCount() throws IOException {
        String content = gson.toJson(categoryService.getAllCategoriesByProductsCount());

        writeToFile(PATH + CATEGORY_BY_PRODUCTS,content);

    }

    private void usersSoldProducts() throws IOException {

        List<UserWithSoldProductsDto> products = userService.findAllBySoldProductsSize();

        System.out.println(products.size());

        String content = gson.toJson(products);

       writeToFile(PATH + USERS_SOLD_PRODUCTS,content);
    }

    private void productsInRange() throws IOException {

        List<ProductNameAndPriceAndSellerDto> productsInRange = this.productService
                .findProductsInRange(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));

        String content = gson.toJson(productsInRange);

        writeToFile(PATH + PRODUCT_IN_RANGE_PATH,content);

    }

    private void writeToFile(String path, String content) throws IOException {

        Files.write(Path.of(path),
                Collections.singleton(content));
    }

    private void seedData() throws IOException {
        categoryService.seed();
        userService.seed();
        productService.seed();
//        supplierService.seed();
//        customerService.seed();
//        partService.seed();
//        carService.seed();
//        saleService.seed();


   }
}
