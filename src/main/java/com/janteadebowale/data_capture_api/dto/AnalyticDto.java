package com.janteadebowale.data_capture_api.dto;

/**********************************************************
 2024 Copyright (C),  JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-service
 * Package   : com.janteadebowale.data_capture_api.dto
 **********************************************************/
public class AnalyticDto {
    private int totalCapture;
    private int approvedCapture;
    private int rejectedCapture;

    public int getTotalCapture() {
        return totalCapture;
    }

    public void setTotalCapture(int totalCapture) {
        this.totalCapture = totalCapture;
    }

    public int getApprovedCapture() {
        return approvedCapture;
    }

    public void setApprovedCapture(int approvedCapture) {
        this.approvedCapture = approvedCapture;
    }

    public int getRejectedCapture() {
        return rejectedCapture;
    }

    public void setRejectedCapture(int rejectedCapture) {
        this.rejectedCapture = rejectedCapture;
    }
}
