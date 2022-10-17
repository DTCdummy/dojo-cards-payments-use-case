package com.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
        		.groupName("payment-api")
        		.apiInfo(getInfo())
        		.select()
        		.apis(RequestHandlerSelectors.basePackage("com.payment.controller"))
        		.build();
	}
	
	private ApiInfo getInfo() {
		return new ApiInfoBuilder().title("Cards & Payments - Payment API")
                .description("Payment-API will make payments.")
                .termsOfServiceUrl("TBD")
                .contact(new Contact("Team Incredibles", "https://www.cognizant.com", "Prithviraj.Gadgi@cognizant.com"))
                .license("License of API").version("1.0").build();
	}
}