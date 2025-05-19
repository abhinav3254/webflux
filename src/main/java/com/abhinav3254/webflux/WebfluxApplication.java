package com.abhinav3254.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;


// this we are telling spring to scan which package and this ${sec} we will passing
// using our application.properties
@SpringBootApplication(scanBasePackages = "com.abhinav3254.webflux.${sec}")
// r2dbc have their own they don't understand spring properties
@EnableR2dbcRepositories(basePackages = "com.abhinav3254.webflux.${sec}")
public class WebfluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxApplication.class, args);
	}

}
