package com.janteadebowale.data_capture_api.controller;

import com.janteadebowale.data_capture_api.dto.SignupDto;
import com.janteadebowale.data_capture_api.dto.Response;
import com.janteadebowale.data_capture_api.service.SignupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**********************************************************
 2024 Copyright (C),  JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-api
 * Package   : com.janteadebowale.data_capture_api.controller
 **********************************************************/
@RestController
@RequestMapping("/api/signup")
@Tag(name = "Signup")
public class SignupController {
    private final SignupService signupService;

    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    @Operation(
            description = "Signup",
            summary = "This is used to register new user"
    )
    @PostMapping()
    public ResponseEntity<Response> signup(@io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            mediaType = "application/json"
    )) @Valid @RequestBody SignupDto signupDto) {
        return ResponseEntity.ok(signupService.signupUser(signupDto));
    }
}
