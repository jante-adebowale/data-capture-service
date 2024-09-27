package com.janteadebowale.data_capture_api.controller;

import com.janteadebowale.data_capture_api.dto.AddCaptureDto;
import com.janteadebowale.data_capture_api.dto.Response;
import com.janteadebowale.data_capture_api.service.impl.CaptureServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/api/captures")
@Tag(name = "Data Capture")
public class CaptureController {
    private final CaptureServiceImpl captureService;

    public CaptureController(CaptureServiceImpl captureService) {
        this.captureService = captureService;
    }

    @Operation(
            summary = "Upload captured data"
    )
    @PostMapping()
    public ResponseEntity<Response> addCapture(@io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            mediaType = "application/json"
    )) @Valid @RequestBody AddCaptureDto addCaptureDto) {
        return ResponseEntity.ok(captureService.addCapture(addCaptureDto));
    }

    @Operation(
            summary = "Uploaded data analytics"
    )
    @GetMapping("/analytics")
    public ResponseEntity<Response> getAnalytic() {
        return ResponseEntity.ok(captureService.getCaptureAnalytic());
    }

    @Operation(
            summary = "Query approved/rejected upload data"
    )
    @GetMapping
    public ResponseEntity<Response> getCaptures(@RequestParam("status") String status) {
        if (status.equalsIgnoreCase("approved")) {
            return ResponseEntity.ok(captureService.getAllApprovedCaptures());
        } else {
            return ResponseEntity.ok(captureService.getAllDeclinedCaptures());
        }
    }
}
