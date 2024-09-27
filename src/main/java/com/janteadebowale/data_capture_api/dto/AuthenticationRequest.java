package com.janteadebowale.data_capture_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**********************************************************
 2024 Copyright (C),  JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-service
 * Package   : com.janteadebowale.data_capture_api.dto
 **********************************************************/
public class AuthenticationRequest {
    @Valid
    @NotBlank(message = "Email can't be empty")
    @NotNull(message = "Email can't be empty")
    private String email;
    @NotBlank(message = "Password can't be empty")
    @NotNull(message = "Password can't be empty")
    private String password;

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
