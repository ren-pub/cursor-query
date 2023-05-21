package com.mlamp.cursor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CursorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CursorApplication.class, args);
    }

}
