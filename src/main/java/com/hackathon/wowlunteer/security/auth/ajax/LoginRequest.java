package com.hackathon.wowlunteer.security.auth.ajax;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {
    private String email;
    private String password;

    @JsonCreator
    public LoginRequest(@JsonProperty("email") String email, @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}