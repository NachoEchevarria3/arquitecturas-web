package com.app.micromonopatin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicroMonopatinApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroMonopatinApplication.class, args);
    }

}
