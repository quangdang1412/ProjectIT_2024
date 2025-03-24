package com.webbanhang.webbanhang;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


import java.io.FileNotFoundException;

@SpringBootApplication
@EnableCaching
public class WebbanhangApplication {

    public static void main(String[] args) throws FileNotFoundException {
        SpringApplication.run(WebbanhangApplication.class, args);
    }

}
