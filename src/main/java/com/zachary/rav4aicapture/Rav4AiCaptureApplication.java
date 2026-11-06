package com.zachary.rav4aicapture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The SpringBootApplication annotation indicates this class is the primary bootstrap class and combines 3 annotations:
    * SpringBootConfiguration: allows this class to specify configurations for this Spring application.
    * EnableAutoConfiguration: detects and creates various configurations (sets up web MVC, tomcat server, etc.).
    * ComponentScan: ensures that component scanning starts from THIS package.
 * When run() executes, Spring Boot creates the app environment, creates the Spring ApplicationContext loads configs, performs component scanning, completes app startup, etc.
 */

@SpringBootApplication
public class Rav4AiCaptureApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                Rav4AiCaptureApplication.class,
                args
        );

    }

}