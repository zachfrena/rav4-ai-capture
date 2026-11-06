package com.zachary.rav4aicapture.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zachary.rav4aicapture.config.GeminiConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * An api client class.
 * The Component annotation enables this class to be created/managed as a bean by Spring and made available in the Application Context.
 *
 */

@Component
public class GeminiClient {

    private final GeminiConfig config;

    private final RestTemplate restTemplate; // HTTP client used to communicate with Gemini

    private final ObjectMapper objectMapper; // parses the json response

    public GeminiClient(GeminiConfig config, ObjectMapper objectMapper) {
        this.config = config;
        this.restTemplate = new RestTemplate();
        this.objectMapper = objectMapper;
        System.out.println("Gemini model = " + config.getModel());
        System.out.println("Gemini key prefix = " +
                config.getApiKey().substring(0, 8)); // for validation in the standard output
    }

    public String extract(String prompt) { //passes a prompt to the gemini url and returns the text response

        String url =
                "https://generativelanguage.googleapis.com/v1beta/models/"
                        + config.getModel()
                        + ":generateContent?key="
                        + config.getApiKey();

        String response = restTemplate.postForObject(
                url,
                buildRequest(prompt),
                String.class
        );
        try {

            JsonNode root = objectMapper.readTree(response);

            return root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Gemini response", e);
        }
    }

    private Map<String, Object> buildRequest(String prompt) {

        return Map.of(
                "contents",
                List.of(
                        Map.of(
                                "parts",
                                List.of(
                                        Map.of("text", prompt)
                                )
                        )
                )
        );
    }
}