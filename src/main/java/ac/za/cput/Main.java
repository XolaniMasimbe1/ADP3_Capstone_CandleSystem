package ac.za.cput;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
/*
 * Main.java
 *
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 26 June 2025
 **/
@SpringBootApplication(scanBasePackages = "ac.za.cput")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}