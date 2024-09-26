package com.janteadebowale.data_capture_api.exception;

/**********************************************************
 2024 Copyright (C),  JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-api
 * Package   : com.janteadebowale.data_capture_api.exception
 **********************************************************/
public class DuplicateEntityException extends Exception{
    public DuplicateEntityException(String message) {
        super(message);
    }
}