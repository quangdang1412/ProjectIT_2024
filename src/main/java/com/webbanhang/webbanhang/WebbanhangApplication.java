package com.webbanhang.webbanhang;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.StringRedisTemplate;


import java.io.FileNotFoundException;

@SpringBootApplication
//@EnableCaching
public class WebbanhangApplication {

    public static void main(String[] args) throws FileNotFoundException {
        SpringApplication.run(WebbanhangApplication.class, args);
    }


}
