package com.jennifer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GeneaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeneaApplication.class, args);
	}

}
