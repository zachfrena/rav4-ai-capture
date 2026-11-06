package com.zachary.rav4aicapture.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * A Spring-managed configuration class.
 * Retrieves api-key and model configuration values from application.yml.
 * The configuration annotation allows Spring to create/manage a GeminiConfig bean, allowing for injection into other components.
 * Provides getter methods for other classes to fetch these encapsulated values.
 *
 */

@Configuration
public class GeminiConfig {


    @Value("${gemini.api-key}")
    private String apiKey;


    @Value("${gemini.model}")
    private String model;


    public String getApiKey() {
        return apiKey;
    }


    public String getModel() {
        return model;
    }
}