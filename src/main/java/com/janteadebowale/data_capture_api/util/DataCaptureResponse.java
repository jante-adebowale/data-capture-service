package com.janteadebowale.data_capture_api.util;

import com.janteadebowale.data_capture_api.dto.Response;

/**********************************************************
 2024 Copyright (C),  JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-api
 * Package   : com.janteadebowale.data_capture_api.util
 **********************************************************/
public class DataCaptureResponse {
    public static Response getResponse(boolean success){
        if(success){
            return new Response(true,"Successful",null);
        }else{
            return new Response(false,"Failed Request",null);
        }
    }
}
