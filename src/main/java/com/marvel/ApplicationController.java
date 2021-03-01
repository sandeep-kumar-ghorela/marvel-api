package com.marvel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ApplicationController
{
    public static void main(String... args)
    {
        SpringApplication.run(ApplicationController.class, args);
    }
}
