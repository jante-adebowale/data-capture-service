package com.janteadebowale.data_capture_api.service;

import com.janteadebowale.data_capture_api.dto.AddCaptureDto;
import com.janteadebowale.data_capture_api.dto.Response;

/************************************************************
 2024 Copyright (C), JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                    
 ************************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-service
 * Youtube   : https://www.youtube.com/@jante-adebowale
 * Github    : https://github.com/jante-adebowale
 ************************************************************/
public interface CaptureService {
    Response addCapture(AddCaptureDto addCaptureDto);

    Response getAllApprovedCaptures();

    Response getCaptureAnalytic();

    Response getAllDeclinedCaptures();
}
