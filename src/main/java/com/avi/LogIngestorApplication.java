package com.avi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static com.avi.constants.PackageConstants.*;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = {CONSTANTS, CONTROLLER, DTO, EXCEPTION, MAPPER, POJO, REPO, SERVICE, CONFIG, UTIL})
public class LogIngestorApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogIngestorApplication.class, args);
    }

}
