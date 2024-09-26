package com.janteadebowale.data_capture_api.advice;

import com.janteadebowale.data_capture_api.exception.AccessDeniedException;
import com.janteadebowale.data_capture_api.exception.DuplicateEntityException;
import com.janteadebowale.data_capture_api.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**********************************************************
 2024 Copyright (C),  JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-api
 * Package   : com.janteadebowale.data_capture_api.advice
 **********************************************************/
@RestController
public class DataCaptureExceptionHandler {
    private static final  String MESSAGE = "message";
    private static final  String SUCCESS = "success";
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleValidationException(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error ->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });
        return  errors;
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public Map<String,Object> handleEntityNotFoundException(EntityNotFoundException ex){
        Map<String,Object> errors = new HashMap<>();
        errors.put(SUCCESS,false);
        errors.put(MESSAGE,ex.getMessage());
        errors.put("data","{}");
        return  errors;
    }


    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateEntityException.class)
    public Map<String,Object> handleDubplicateEntityException(DuplicateEntityException ex){
        Map<String,Object> errors = new HashMap<>();
        errors.put(SUCCESS,false);
        errors.put(MESSAGE,ex.getMessage());
        errors.put("data","{}");
        return  errors;
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public Map<String,Object> handleAccessDeniedException(AccessDeniedException ex){
        Map<String,Object> errors = new HashMap<>();
        errors.put(SUCCESS,false);
        errors.put(MESSAGE,ex.getMessage());
        errors.put("data","{}");
        return  errors;
    }
}
