package com.example.jsonprocessing.carDealer.repository;

import com.example.jsonprocessing.carDealer.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {


    @Query("SELECT c FROM cars c " +
            "WHERE c.make = :make " +
            "ORDER BY c.model,c.travelledDistance DESC")
    List<Car> findAllByMakeOrderByModelTravelledDistanceDesc(String make);

    @Query("SELECT c FROM cars c")
    List<Car> getAllCarsAndParts();


}
