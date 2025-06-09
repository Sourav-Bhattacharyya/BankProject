package com.sourav.banking.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class RegisterRequest {

    @NotNull
    String username;

    @NotNull
    long number;

    @Email
    String email;


    String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
