package com.spring.eac.ai;

import com.spring.eac.ai.property.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class SpringAiIETLApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiIETLApplication.class, args);
    }

}

