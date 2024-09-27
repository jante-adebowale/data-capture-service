package com.janteadebowale.data_capture_api.controller;

import com.janteadebowale.data_capture_api.service.AuthService;
import com.janteadebowale.data_capture_api.service.impl.LogoutServiceImpl;
import com.janteadebowale.data_capture_api.dto.*;
import com.janteadebowale.data_capture_api.exception.AccessDeniedException;
import com.janteadebowale.data_capture_api.service.impl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**********************************************************
 2024 Copyright (C),  JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-service
 * Package   : com.janteadebowale.data_capture_api.controller
 **********************************************************/
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth")
public class AuthController {
    private final AuthService authService;
    private final LogoutServiceImpl authenticationService;

    public AuthController(AuthService authService, LogoutServiceImpl authenticationService) {
        this.authService = authService;
        this.authenticationService = authenticationService;
    }

    @Operation(
            summary = "User login"
    )
    @PostMapping()
    public ResponseEntity<Response> auth(@io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            mediaType = "application/json",
            examples = {
                    @ExampleObject(
                            value = "{\"email\": \"jante.adebowale@gmail.com\",\"password\": \"password\"}"
                    )
            }
    )) @Valid @RequestBody AuthenticationRequest authenticationRequest) {
        Response authenticate = authService.authenticate(authenticationRequest);
        if (authenticate != null) {
            return ResponseEntity.ok(authenticate);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(
            summary = "Refresh user access token"
    )
    @PostMapping("/refresh-token")
    public ResponseEntity<AccessTokenResponse> refreshToken(@io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            mediaType = "application/json",
            examples = {
                    @ExampleObject(
                            value = "{\"refreshToken\": \"\",\"userId\": \"\"}"
                    )
            }
    )) @Valid @RequestBody AccessTokenRequest authenticationRequest) throws AccessDeniedException {
        return ResponseEntity.ok(authService.refreshAccessToken(authenticationRequest));
    }

    @Operation(
            summary = "Change user's password"
    )
    @PostMapping("/change-password")
    public ResponseEntity<Response> changePassword(@io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            mediaType = "application/json",
            examples = {
                    @ExampleObject(
                            value = "{\"oldPassword\": \"password\",\"newPassword\": \"password\"}"
                    )
            }
    )) @Valid @RequestBody ChangePasswordDto changePassword) {
        return ResponseEntity.ok(authService.changePassword(changePassword));
    }

    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Logout")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "logout successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))})
    })
    public ResponseEntity<Response> logout(HttpServletRequest request, @RequestBody @Valid LogoutRequest logoutRequest) {
        authenticationService.logout(request,null,null);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).build();
    }

}
