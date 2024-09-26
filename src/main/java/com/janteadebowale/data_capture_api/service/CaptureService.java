package com.janteadebowale.data_capture_api.service;

import com.janteadebowale.data_capture_api.dto.AddCaptureDto;
import com.janteadebowale.data_capture_api.dto.AnalyticDto;
import com.janteadebowale.data_capture_api.dto.Response;
import com.janteadebowale.data_capture_api.model.Analytic;
import com.janteadebowale.data_capture_api.model.Capture;
import com.janteadebowale.data_capture_api.model.User;
import com.janteadebowale.data_capture_api.repository.CaptureRepository;
import com.janteadebowale.data_capture_api.util.DataCaptureResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**********************************************************
 2024 Copyright (C),  JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-api
 * Package   : com.janteadebowale.data_capture_api.service
 **********************************************************/
@Service
public class CaptureService {
    private final CaptureRepository captureRepository;

    public CaptureService(CaptureRepository captureRepository) {
        this.captureRepository = captureRepository;
    }

    public Response addCapture(AddCaptureDto addCaptureDto) {
        Response response = null;
        try {
            User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userId = userPrincipal.getId();

            Boolean captureExist = captureRepository.isCaptureExist(addCaptureDto.getId());
            if (captureExist) {
                response = DataCaptureResponse.getResponse(true);
                response.setMessage("Capture Exist");
                return response;
            }

            Capture capture = new Capture();
            capture.setFirstname(addCaptureDto.getFirstname().trim());
            capture.setLastname(addCaptureDto.getLastname());
            capture.setUserId(userId);
            capture.setLongitude(addCaptureDto.getLongitude());
            capture.setLatitude(addCaptureDto.getLatitude());
            capture.setId(addCaptureDto.getId());
            capture.setApproved(true);
            capture.setAge(addCaptureDto.getAge());
            captureRepository.addCapture(capture);

            response = DataCaptureResponse.getResponse(true);
            response.setMessage("Capture saved successfully");

        } catch (Exception e) {
            e.printStackTrace();
            response = DataCaptureResponse.getResponse(false);
            response.setMessage("Internal Server Error!");
        }
        return response;
    }


    public Response getAllApprovedCaptures() {
        Response response = null;
        try {
            List<Capture> approvedCaptures = captureRepository.getApprovedCaptures();
            if (approvedCaptures != null) {
                response = DataCaptureResponse.getResponse(true);
                response.setMessage("Successful");
                response.setData(approvedCaptures);
            } else {
                response = DataCaptureResponse.getResponse(false);
                response.setMessage("No Capture found.");
            }
        } catch (Exception e) {
            response = DataCaptureResponse.getResponse(false);
            response.setMessage("Internal Server Error!");
        }

        return response;
    }

    public Response getCaptureAnalytic() {
        Response response = null;

        try {
            User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userId = userPrincipal.getId();
            List<Analytic> analytics = captureRepository.getCaptureAnalytics(userId);
            int totalCapture = 0;
            AnalyticDto analyticDto = new AnalyticDto();
            if (analytics != null && !analytics.isEmpty()) {
                for(Analytic analytic : analytics){
                    if(analytic.isStatus()){
                        totalCapture += analytic.getCount();
                        analyticDto.setApprovedCapture(analytic.getCount());
                    }else {
                        totalCapture += analytic.getCount();
                        analyticDto.setRejectedCapture(analytic.getCount());
                    }
                }
                analyticDto.setTotalCapture(totalCapture);
            }
            response = DataCaptureResponse.getResponse(true);
            response.setMessage("Successful");
            response.setData(analyticDto);
        } catch (Exception e) {
            response = DataCaptureResponse.getResponse(false);
            response.setMessage("Internal Server Error!");
        }
        return response;
    }


    public Response getAllDeclinedCaptures() {
        Response response = null;
        try {
            List<Capture> declinedCaptures = captureRepository.getDeclinedCaptures();
            if (declinedCaptures != null) {
                response = DataCaptureResponse.getResponse(true);
                response.setMessage("Successful");
                response.setData(declinedCaptures);
            } else {
                response = DataCaptureResponse.getResponse(false);
                response.setMessage("No Capture found.");
            }
        } catch (Exception e) {
            response = DataCaptureResponse.getResponse(false);
            response.setMessage("Internal Server Error!");
        }
        return response;
    }

}
