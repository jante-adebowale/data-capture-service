package com.janteadebowale.data_capture_api.service.impl;

import com.janteadebowale.data_capture_api.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

/**********************************************************
 2024 Copyright (C),  JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-service
 * Package   : com.janteadebowale.data_capture_api.config
 **********************************************************/
@Service
public class LogoutServiceImpl implements LogoutHandler {
    private final TokenRepository tokenRepository;

    public LogoutServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;

        if (authHeader != null && !authHeader.isEmpty() && authHeader.startsWith("Bearer ")) {

            jwtToken = authHeader.substring(7);
            var token = tokenRepository.getTokenByToken(jwtToken).orElse(null);
            if(token != null ){
                if(!token.logout()) {
                    tokenRepository.logOut(token.id());
                }else{
                }
            }
        }else{
            return;
        }
    }
}
