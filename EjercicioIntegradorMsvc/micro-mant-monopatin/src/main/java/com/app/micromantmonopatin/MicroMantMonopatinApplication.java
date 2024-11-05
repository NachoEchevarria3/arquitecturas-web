package com.app.micromantmonopatin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicroMantMonopatinApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroMantMonopatinApplication.class, args);
    }

}
