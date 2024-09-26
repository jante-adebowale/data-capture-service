package com.janteadebowale.data_capture_api.model;

/**********************************************************
 2024 Copyright (C),  JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-api
 * Package   : com.janteadebowale.data_capture_api.model
 **********************************************************/
public record Token(
        int id,
        String userId,
        String token,
        String refreshToken,
        boolean expired,
        boolean revoked,
        boolean logout
) {
}
