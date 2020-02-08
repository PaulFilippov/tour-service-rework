package com.paul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TourServiceApplication {
    public static void main(String[] args) {
        System.out.println("турсервисапликатион старт");
        SpringApplication.run(TourServiceApplication.class, args);
        System.out.println("турсервисапликатион стоп");
    }
}
