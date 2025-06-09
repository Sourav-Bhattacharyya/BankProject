package com.sourav.banking.DTO;

public class AuthResponse {
    String token;

    public AuthResponse(String token){
        this.token = token;
    }

    String getToken(){
        return token;
    }
}
