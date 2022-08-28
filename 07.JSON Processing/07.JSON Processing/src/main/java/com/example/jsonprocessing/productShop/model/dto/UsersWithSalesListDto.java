package com.example.jsonprocessing.productShop.model.dto;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class UsersWithSalesListDto {

    @Expose
    private int count;
    @Expose
    private List<UsersFirstAndLastNameDto> users;

    public UsersWithSalesListDto() {
        this.users = new ArrayList<>();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<UsersFirstAndLastNameDto> getUsers() {
        return users;
    }

    public void setUsers(List<UsersFirstAndLastNameDto> users) {
        this.users = users;
    }
}
