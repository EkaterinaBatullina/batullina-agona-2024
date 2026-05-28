package org.example;

import org.example.config.ApplicationConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext configurableApplicationContext = new AnnotationConfigApplicationContext();
        configurableApplicationContext.register(ApplicationConfig.class);
        configurableApplicationContext.refresh();
       /*
       FirstBean initialized
       Bean name - firstBean; Bean class - FirstBean
       SecondBean initialized
       Bean name - secondBean; Bean class - SecondBean
       */
        configurableApplicationContext.close();
    }

}

