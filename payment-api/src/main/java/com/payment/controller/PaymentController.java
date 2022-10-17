package com.payment.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.payment.model.Card;
import com.payment.model.CustomerResponse;
import com.payment.model.Payment;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Api
@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private RestTemplate restTemplate;

	static final String CARD_API_URL = "http://localhost:8010/cards-api/card/";

	static final String CUSTOMER_API_URL = "http://localhost:8010/dojo-customer-apis/customer/";

	@ApiOperation(value = "Test payment-api", hidden = true)
	@GetMapping("/test")
	public String test() {
		return "Payment API is working...";
	}

	@ApiOperation(value = "Make payment")
	@PostMapping("/makepayment")
	public ResponseEntity<String> makePayment(@Valid @RequestBody Payment payment,
			@RequestHeader(value = "Authorization") String token) {
		CustomerResponse customerResponse = restTemplate
				.exchange(CUSTOMER_API_URL + "token", HttpMethod.GET, null, CustomerResponse.class).getBody();
		if (customerResponse.getToken().equals(token)) {
			log.info("Initiating transaction for card number: {}", payment.getCardNumber());
			
			Card card = restTemplate.exchange(CARD_API_URL + payment.getCardNumber(), HttpMethod.GET, null, Card.class)
					.getBody();
			
			if (card.getCardNumber().equals(payment.getCardNumber()) && card.getCardType().equals(payment.getCardType())
					&& card.getExpirationDate().equals(payment.getExpirationDate())
					&& card.getFkCustomerId().equals(payment.getCustomerId())) {
				log.info("Payment Successful");
				return new ResponseEntity<String>("Payment successful!!!", HttpStatus.OK);
			} else {
				log.debug("Payment failed. Error Code: {}", HttpStatus.BAD_REQUEST);
				return new ResponseEntity<String>("Invalid data, Payment failed!!!", HttpStatus.BAD_REQUEST);
			}
		} else {
			log.error("Token Error: Invalid/Tempered Token");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

}
