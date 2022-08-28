package com.example.advquerying.service;

import com.example.advquerying.entities.Size;
import com.example.advquerying.repository.IngredientRepo;
import com.example.advquerying.repository.ShampooRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShampooServiceImpl implements ShampooService{

    private final ShampooRepo shampooRepo;
    private final IngredientRepo ingredientRepo;

    public ShampooServiceImpl(ShampooRepo shampooRepo, IngredientRepo ingredientRepo) {
        this.shampooRepo = shampooRepo;
        this.ingredientRepo = ingredientRepo;
    }


    @Override
    public List<String> findAllBySizeOrderById(Size size) {

        return this.shampooRepo
                .findAllBySizeOrderById(size)
                .stream()
                .map(s ->
                        String.format("%s %s %.2flv",
                                s.getBrand(),
                                s.getSize(),
                                s.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBySizeOrLabelIdOrderByPrice(Size size, Long id) {
        return this.shampooRepo
                .findAllBySizeOrLabelIdOrderByPrice(size,id)
                .stream()
                .map(s ->
                        String.format("%s %s %.2flv",
                                s.getBrand(),
                                s.getSize(),
                                s.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price) {
        return this.shampooRepo
                .findAllByPriceGreaterThanOrderByPriceDesc(price)
                .stream()
                .map(s ->
                        String.format("%s %s %.2flv",
                                s.getBrand(),
                                s.getSize(),
                                s.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByNameStartingWith(char symbol) {
        return this.ingredientRepo
                .findAllByNameStartingWith(symbol)
                .stream()
                .map(i ->
                        String.format("%s",
                                i.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Integer countAllByPriceLessThan(BigDecimal price) {
        return this.shampooRepo
                .countAllByPriceLessThan(price);
    }

    @Override
    public List<String> findAllByIngredientsNames(List<String> names) {
        return this.shampooRepo
                .findAllByIngredientsNames(names)
                .stream()
                .map(s ->
                        String.format("%s",
                                s.getBrand()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByIngredientsLessThan(long number) {
        return this.shampooRepo
                .findAllByIngredientsLessThan(number)
                .stream()
                .map(s -> String.format("%s",s.getBrand()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAllByName(String name) {
        this.shampooRepo.deleteAllByName(name);
    }

    @Override
    @Transactional
    public void updateIngredientPrice() {

        this.shampooRepo.updateIngredientPrice();
    }

    @Override
    @Transactional
    public void updateIngredientPriceByNames(List<String> names) {

        this.shampooRepo.updateIngredientPriceByNames(names);
    }

}
