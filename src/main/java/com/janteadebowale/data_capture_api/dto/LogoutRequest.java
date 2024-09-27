package com.janteadebowale.data_capture_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/************************************************************
 2024 Copyright (C), JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                    
 ************************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-service
 * Youtube   : https://www.youtube.com/@jante-adebowale
 * Github    : https://github.com/jante-adebowale
 ************************************************************/
public class LogoutRequest {
    @NotBlank(message = "Token can't be empty")
    @NotNull(message = "Token can't be empty")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
