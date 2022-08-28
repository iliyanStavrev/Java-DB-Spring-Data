package com.example.advquerying;

import com.example.advquerying.entities.Size;
import com.example.advquerying.repository.IngredientRepo;
import com.example.advquerying.service.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@Service
public class ConsoleRunner implements CommandLineRunner {
    Scanner scanner = new Scanner(System.in);

    private final ShampooService shampooService;
    private final IngredientRepo ingredientRepo;

    public ConsoleRunner(ShampooService shampooService, IngredientRepo ingredientRepo) {
        this.shampooService = shampooService;
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public void run(String... args) throws Exception {

       // task_1();
       // task_2();
       // task_3();
       // task_4();
       // task_6();
       // task_7();
       // task_8();
       // task_9();
       // task_10();
        task_11();

    }

    private void task_11() {
        List<String> names = List.of(scanner.nextLine().split("\\s+"));
        this.shampooService.updateIngredientPriceByNames(names);
    }

    private void task_10() {
        this.shampooService.updateIngredientPrice();
    }

    private void task_9() {
        String name = scanner.nextLine();
        this.shampooService.deleteAllByName(name);
    }

    private void task_8() {
        long number = Long.parseLong(scanner.nextLine());
        this.shampooService
                .findAllByIngredientsLessThan(number)
                .forEach(System.out::println);
    }

    private void task_7() {
        List<String> names = List.of(scanner.nextLine().split(" "));
        this.shampooService
                .findAllByIngredientsNames(names)
                .forEach(System.out::println);
    }

    private void task_6() {
        System.out.println("TASK 6");
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(scanner.nextLine()));

        System.out.println(this.shampooService
                .countAllByPriceLessThan(price));
    }

    private void task_4() {
        System.out.println("TASK 4");
        char symbol = scanner.nextLine().charAt(0);
        this.ingredientRepo
                .findAllByNameStartingWith(symbol)
                .forEach(System.out::println);
    }

    private void task_3() {
        System.out.println("TASK 3");
        BigDecimal price = BigDecimal.valueOf(Long.parseLong(scanner.nextLine()));
        this.shampooService
                .findAllByPriceGreaterThanOrderByPriceDesc(price)
                .forEach(System.out::println);
    }

    private void task_2() {
        System.out.println("TASK 2");
        Size size = Size.valueOf(scanner.nextLine());
        Long id = Long.parseLong(scanner.nextLine());
        this.shampooService
                .findAllBySizeOrLabelIdOrderByPrice(size, id)
                .forEach(System.out::println);
    }

    private void task_1() {
        String input = scanner.nextLine();
        Size size = Size.valueOf(input);
        System.out.println("TASK 1");

        this.shampooService
                .findAllBySizeOrderById(size)
                .forEach(System.out::println);
    }
}
