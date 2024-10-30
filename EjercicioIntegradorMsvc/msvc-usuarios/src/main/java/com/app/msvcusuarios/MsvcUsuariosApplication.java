package com.app.msvcusuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsvcUsuariosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcUsuariosApplication.class, args);
    }

}
