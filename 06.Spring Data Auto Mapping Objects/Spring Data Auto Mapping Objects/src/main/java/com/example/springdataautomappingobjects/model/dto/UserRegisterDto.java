package com.example.springdataautomappingobjects.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegisterDto {

    private String email;
    private String password;
    private String confirmPass;
    private String fullName;

    public UserRegisterDto() {
    }

    public UserRegisterDto(String email, String password,
                           String confirmPass, String fullName) {
        this.email = email;
        this.password = password;
        this.confirmPass = confirmPass;
        this.fullName = fullName;
    }

    @Email(message = "Enter valid email!")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Pattern(regexp = "[A-Za-z\\d]{6,}",message = "Enter valid password!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

    @Size(min = 3, message = "Full name must be at least  3 character long!")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
