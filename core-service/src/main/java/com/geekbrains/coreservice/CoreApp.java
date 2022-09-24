package com.geekbrains.coreservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableCaching
@PropertySource("secrets.properties")
public class CoreApp {

	public static void main(String[] args) {
		SpringApplication.run(CoreApp.class, args);
	}
}
