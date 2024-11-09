package com.app.microparada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicroParadaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroParadaApplication.class, args);
    }

}
