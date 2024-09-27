package com.janteadebowale.data_capture_api.service.impl;

import com.janteadebowale.data_capture_api.dto.SignupDto;
import com.janteadebowale.data_capture_api.dto.Response;
import com.janteadebowale.data_capture_api.enums.Role;
import com.janteadebowale.data_capture_api.model.User;
import com.janteadebowale.data_capture_api.repository.UserRepository;
import com.janteadebowale.data_capture_api.service.SignupService;
import com.janteadebowale.data_capture_api.util.DataCaptureResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**********************************************************
 2024 Copyright (C),  JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-service
 * Package   : com.janteadebowale.data_capture_api.service
 **********************************************************/
@Service
public class SignupServiceImpl implements SignupService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Response signupUser(SignupDto signupDto) {
        Response response = null;
        try {

            Boolean emailExist = userRepository.isEmailExist(signupDto.getEmail());
            if (emailExist) {
                response = DataCaptureResponse.getResponse(false);
                response.setMessage("Email already in-use!");
                return response;
            }

            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setFirstname(signupDto.getFirstname().trim());
            user.setLastname(signupDto.getLastname());
            user.setEmail(signupDto.getEmail());
            user.setUsername(signupDto.getEmail());
            user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
            user.setRole(Role.USER);
            userRepository.registerUser(user);

            response = DataCaptureResponse.getResponse(true);
            response.setMessage("Signup was successful");

        } catch (Exception e) {
            e.printStackTrace();
            response = DataCaptureResponse.getResponse(false);
            response.setMessage("Internal Server Error!");
        }
        return response;
    }

}
