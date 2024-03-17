package com.crispyread.core.controllers;

import com.crispyread.core.constants.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    /**
     * health check endpoint
     * @return String
     */
    @GetMapping(path = "/health")
    public ResponseEntity<String> healthCheck(){
        return new ResponseEntity<>(Constants.HEALTH_CHECK_OK, HttpStatus.OK);
    }

}
