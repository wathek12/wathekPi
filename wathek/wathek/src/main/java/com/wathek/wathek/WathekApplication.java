package com.wathek.wathek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class WathekApplication {

    public static void main(String[] args) {
        SpringApplication.run(WathekApplication.class, args);
    }

}
