package com.vanart.okxnotificationbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OkxNotificationBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(OkxNotificationBotApplication.class, args);
    }

}
