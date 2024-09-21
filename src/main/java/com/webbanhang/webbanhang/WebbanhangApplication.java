package com.webbanhang.webbanhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

@SpringBootApplication
public class WebbanhangApplication {
	public static void main(String[] args) throws FileNotFoundException {

		SpringApplication.run(WebbanhangApplication.class, args);
	}

}
