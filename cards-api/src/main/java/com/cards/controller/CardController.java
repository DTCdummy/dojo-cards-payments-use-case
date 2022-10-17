package com.cards.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cards.dto.UpdateCard;
import com.cards.model.Card;
import com.cards.model.Customer;
import com.cards.model.CustomerResponse;
import com.cards.service.CardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Api
@RestController
@RequestMapping("/card")
public class CardController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CardService cardService;

	static final String CUSTOMER_API_URL = "http://localhost:8010/dojo-customer-apis/customer/";

	@ApiOperation(value = "Test cards-api", hidden = true)
	@GetMapping("/test")
	public String test() {
		return "Cards API is working...";
	}

	@ApiOperation(value = "Add new card")
	@PostMapping("/{customerId}/add")
	public ResponseEntity<Card> addCard(@Valid @RequestBody Card card, @PathVariable("customerId") String customerId,
			@RequestHeader(value = "Authorization") String token) {
		
		log.debug("Adding Card with card number: {}", card.getCardNumber());
		
		CustomerResponse customerResponse = restTemplate
				.exchange(CUSTOMER_API_URL + "token", HttpMethod.GET, null, CustomerResponse.class).getBody();
		if (customerResponse.getToken().equals(token)) {
			Customer customer = restTemplate
					.exchange(CUSTOMER_API_URL + customerId, HttpMethod.GET, null, Customer.class).getBody();
			card.setFkCustomerId(customer.getCustomerId());
			Card savedCard = cardService.saveCard(card);
			
			log.info("Card with card number: {} added.", card.getCardNumber());
			return new ResponseEntity<Card>(savedCard, HttpStatus.CREATED);
		} else {
			log.error("Token Error: Invalid/Tempered Token");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@ApiOperation(value = "Update existing card")
	@PutMapping("/{cardNumber}/update")
	public ResponseEntity<Card> updateCard(@Valid @RequestBody UpdateCard card,
			@PathVariable("cardNumber") String cardNumber, @RequestHeader(value = "Authorization") String token) {
		CustomerResponse customerResponse = restTemplate
				.exchange(CUSTOMER_API_URL + "token", HttpMethod.GET, null, CustomerResponse.class).getBody();
		if (customerResponse.getToken().equals(token)) {
			Card updatedCard = cardService.updateCard(cardNumber, card.getCardType(), card.getExpirationDate());
			log.info("Card with card number: {} updated.", cardNumber);
			
			return new ResponseEntity<Card>(updatedCard, HttpStatus.OK);
		} else {
			log.error("Token Error: Invalid/Tempered Token");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@ApiOperation(value = "Get card by card number", hidden = true)
	@GetMapping("/{cardNumber}")
	public ResponseEntity<Card> getCard(@PathVariable("cardNumber") String cardNumber) {
		Card card = cardService.getCard(cardNumber);
		log.info("Card with card number: {} found.", cardNumber);
		
		return new ResponseEntity<Card>(card, HttpStatus.OK);
	}

}
