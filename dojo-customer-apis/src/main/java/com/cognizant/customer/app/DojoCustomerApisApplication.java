package com.cognizant.customer.app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
public class DojoCustomerApisApplication {

	public static void main(String[] args) {
		log.info("Initiating Customer API");
		SpringApplication.run(DojoCustomerApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}
