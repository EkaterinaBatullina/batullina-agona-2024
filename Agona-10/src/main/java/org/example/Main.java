package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.example.config.LiquibaseStarterConfig;

@SpringBootApplication
@Import(LiquibaseStarterConfig.class)
@EnableConfigurationProperties
public class Main {

     public static void main(String[] args) {
         SpringApplication.run(Main.class, args);
     }
}