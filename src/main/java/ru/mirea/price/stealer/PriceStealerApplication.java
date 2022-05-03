package ru.mirea.price.stealer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PriceStealerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PriceStealerApplication.class, args);
    }
}
