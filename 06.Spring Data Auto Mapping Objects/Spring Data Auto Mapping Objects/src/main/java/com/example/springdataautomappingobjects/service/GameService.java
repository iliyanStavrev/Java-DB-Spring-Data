package com.example.springdataautomappingobjects.service;

import com.example.springdataautomappingobjects.model.dto.GameAddDto;

public interface GameService {

    public void addGame(GameAddDto gameAddDto);

    void editGame(String [] commands);

    void deleteGame(Long id);

    void printAllGames();

    void detailGame(String title);
}
