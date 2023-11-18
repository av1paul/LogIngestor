package com.avi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class LogIngestorApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogIngestorApplication.class, args);
    }

}
