package com.cards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
@EnableDiscoveryClient
public class CardsApiApplication {

	public static void main(String[] args) {
		log.info("Initiating Cards API ....");
		SpringApplication.run(CardsApiApplication.class, args);
	}

}
