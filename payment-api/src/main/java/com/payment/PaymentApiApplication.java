package com.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
public class PaymentApiApplication {

	public static void main(String[] args) {
		log.info("Initiating Payments API");
		SpringApplication.run(PaymentApiApplication.class, args);
	}

}
