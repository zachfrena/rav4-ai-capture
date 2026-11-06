package com.zachary.rav4aicapture.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HealthController {


    @GetMapping("/health")
    public String health() {

        return "RAV4 AI Capture is running";

    }

}