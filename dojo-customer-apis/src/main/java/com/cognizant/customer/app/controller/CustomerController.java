package com.cognizant.customer.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.customer.app.payload.CustomerDto;
import com.cognizant.customer.app.payload.CustomerRequest;
import com.cognizant.customer.app.payload.CustomerResponse;
import com.cognizant.customer.app.service.CustomerService;
import com.cognizant.customer.app.util.JwtUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Api
@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private JwtUtil util;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	CustomerResponse customerResponse = new CustomerResponse();
	
	@ApiOperation(value = "Test customer-api", hidden = true)
	@GetMapping("/test")
	public String test() {
		return "Customer API is working...";
	}
	
	@ApiOperation(value = "Create new customer")
	@PostMapping(value = "/create", consumes = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_PLAIN_VALUE,
			MediaType.ALL_VALUE
		},
		produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_PLAIN_VALUE,
			MediaType.ALL_VALUE
		}
	)
	public ResponseEntity<CustomerDto> createCustomer (@Valid @RequestBody CustomerDto customerDto){
		CustomerDto createdCustomerDto = this.customerService.createCustomer(customerDto);
		log.info("Customer with id: {}  registered/created", createdCustomerDto.getCustomerId());
		
		return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);
	}
	
	
	@ApiOperation(value = "Login using customerId and password")
	@PostMapping(value = "/login")
	public ResponseEntity<CustomerResponse> loginuser(@RequestBody CustomerRequest request) {

		// validate username/password with database
		this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getCustomerId(), request.getPassword()));
		

		// if no problem, create jwt token
		String token = util.generateToken(request.getCustomerId());
		customerResponse.setMessage("Success! JWT token Generated");
		customerResponse.setToken(token);
		
		log.info("Customer with id: {} successfully login. Token Generated!!!", request.getCustomerId());
		return new ResponseEntity<CustomerResponse>(customerResponse, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Get JWT token", hidden = true)
	@GetMapping(value = "/token")
	public ResponseEntity<CustomerResponse> getToken() {
		
		return new ResponseEntity<CustomerResponse>(customerResponse, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "Get customer by customerId", hidden = true)
	@GetMapping(value = "/{customerId}", consumes = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_PLAIN_VALUE,
			MediaType.ALL_VALUE
		},
		produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_PLAIN_VALUE,
			MediaType.ALL_VALUE
		}
	)
	public ResponseEntity<CustomerDto> getCustomer (@PathVariable("customerId") String customerId){
		CustomerDto foundCustomerDto = this.customerService.getCustomerByCustomerId(customerId);
		log.info("Details of Customer with id: {}  has been accessed", foundCustomerDto.getCustomerId());
		
		return new ResponseEntity<>(foundCustomerDto, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "Update existing customer")
	@PutMapping(value = "/{customerId}/update", consumes = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_PLAIN_VALUE,
			MediaType.ALL_VALUE
		},
		produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_PLAIN_VALUE,
			MediaType.ALL_VALUE
		}
	)
	public ResponseEntity<CustomerDto> updateCustomer (
			@Valid @RequestBody CustomerDto customerDto,
			@PathVariable("customerId") String customerId
			){
		CustomerDto updatedCustomerDto = this.customerService.updateCustomer(customerDto, customerId);
		log.info("Customer with id: {}  has been updated", updatedCustomerDto.getCustomerId());
		
		return new ResponseEntity<>(updatedCustomerDto, HttpStatus.OK);
	}
	
}
