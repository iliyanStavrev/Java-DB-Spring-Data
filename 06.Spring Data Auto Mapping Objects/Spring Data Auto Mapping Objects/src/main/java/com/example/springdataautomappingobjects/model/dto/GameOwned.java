package com.example.springdataautomappingobjects.model.dto;

public class GameOwned {
    private String title;

    public GameOwned() {
    }

    public GameOwned(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
