package com.janteadebowale.data_capture_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**********************************************************
 2024 Copyright (C),  JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-service
 * Package   : com.janteadebowale.data_capture_api.dto
 **********************************************************/
public class SignupDto {
    @Valid
    @NotBlank(message = "First name can't be blank")
    private String firstname;
    @Valid
    @NotBlank(message = "Last name can't be blank")
    private String lastname;
    @Valid
    @Email
    @NotBlank(message = "Email can't be blank")
    private String email;
    @Valid
    @NotBlank(message = "Password can't be blank")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
