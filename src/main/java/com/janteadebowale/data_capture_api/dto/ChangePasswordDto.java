package com.janteadebowale.data_capture_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**********************************************************
 2024 Copyright (C),  JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-api
 * Package   : com.janteadebowale.data_capture_api.dto
 **********************************************************/
public class ChangePasswordDto {
    @Valid
    @NotBlank(message = "Old password can't be blank")
    private String oldPassword;
    @Valid
    @NotBlank(message = "New password can't be blank")
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
