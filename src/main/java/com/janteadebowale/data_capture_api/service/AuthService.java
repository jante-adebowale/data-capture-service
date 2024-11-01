package com.janteadebowale.data_capture_api.service;

import com.janteadebowale.data_capture_api.dto.*;
import com.janteadebowale.data_capture_api.exception.AccessDeniedException;

/************************************************************
 2024 Copyright (C), JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                    
 ************************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-service
 * Youtube   : https://www.youtube.com/@jante-adebowale
 * Github    : https://github.com/jante-adebowale
 ************************************************************/
public interface AuthService {
    Response authenticate(AuthenticationRequest authenticationRequest);
    AccessTokenResponse refreshAccessToken(AccessTokenRequest accessTokenRequest) throws AccessDeniedException;
    Response changePassword(ChangePasswordDto changePassword);

}
