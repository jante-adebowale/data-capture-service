package com.janteadebowale.data_capture_api.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**********************************************************
 2024 Copyright (C),  JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-api
 * Package   : com.janteadebowale.data_capture_api.controller
 **********************************************************/

@RestController
@Hidden
public class HomeController {
    @RequestMapping("/")
    public void redirectHome(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }
}
