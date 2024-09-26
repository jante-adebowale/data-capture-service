package com.janteadebowale.data_capture_api.service;

import com.janteadebowale.data_capture_api.dto.*;
import com.janteadebowale.data_capture_api.exception.AccessDeniedException;
import com.janteadebowale.data_capture_api.model.Token;
import com.janteadebowale.data_capture_api.model.User;
import com.janteadebowale.data_capture_api.repository.TokenRepository;
import com.janteadebowale.data_capture_api.repository.UserRepository;
import com.janteadebowale.data_capture_api.util.DataCaptureResponse;
import com.janteadebowale.data_capture_api.util.DataCaptureUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**********************************************************
 2024 Copyright (C),  JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-api
 * Package   : com.janteadebowale.data_capture_api.service
 **********************************************************/
@Service
public class AuthService {
    @Value("${application.security.jwt.refresh.expiration}")
    private int refreshTokenLifeSpan;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    public Response authenticate(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        var user = userRepository.findByUsername(authenticationRequest.getEmail()).orElseThrow();

        String accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);


        //Insert Token
        tokenRepository.revokeToken(user.getId());

        saveToken(user.getId(), accessToken, refreshToken);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setId(user.getId());
        authenticationResponse.setAccess_token(accessToken);
        authenticationResponse.setRefresh_token(refreshToken);
        authenticationResponse.setFirstname(user.getFirstname());
        authenticationResponse.setLastname(user.getLastname());
        authenticationResponse.setEmail(user.getEmail());

        Response response = DataCaptureResponse.getResponse(true);
        response.setData(authenticationResponse);

        return response;
    }


    public AccessTokenResponse refreshAccessToken(AccessTokenRequest accessTokenRequest) throws AccessDeniedException {
        var user = userRepository.findByUserId(accessTokenRequest.getUserId()).orElseThrow();
        if (!tokenRepository.isRefreshTokenValid(accessTokenRequest.getRefreshToken(), accessTokenRequest.getUserId())) {
            throw new AccessDeniedException("");
        }

        String accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        //Insert Token
        tokenRepository.revokeToken(user.getId());

        saveToken(user.getId(), accessToken, refreshToken);

        Date expirationTime = DataCaptureUtil.createLifeSpanFromNowInSeconds(refreshTokenLifeSpan);

        AccessTokenResponse accessTokenResponse = new AccessTokenResponse();
        accessTokenResponse.setAccess_Token(accessToken);
        accessTokenResponse.setRefresh_Token(refreshToken);
        return accessTokenResponse;
    }


    private void saveToken(String userId, String jwt, String refreshToken) {
        Token token = new Token(0, userId, jwt, refreshToken, false, false, false);
        tokenRepository.saveToken(token);
    }

    public Response changePassword(ChangePasswordDto changePassword) {
        Response response = null;
        try {
            User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userId = userPrincipal.getId();
            Optional<User> userOptional = userRepository.findByUserId(userId);
            if (!userOptional.isPresent()) {
                response = DataCaptureResponse.getResponse(false);
                response.setMessage("Invalid user credentials");
                return response;
            }

            User user = userOptional.get();

            if (!passwordEncoder.matches(changePassword.getOldPassword(), user.getPassword())) {
                response = DataCaptureResponse.getResponse(false);
                response.setMessage("Wrong current password");
                return response;
            }

            user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
            userRepository.updateUserPassword(user);
            response = DataCaptureResponse.getResponse(true);
            response.setData("Password Changed Successsfully");
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            response = DataCaptureResponse.getResponse(false);
            response.setMessage("Internal Server Error!");
            return response;
        }
    }

}
