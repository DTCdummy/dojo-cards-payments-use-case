package com.cognizant.customer.app.service;

import com.cognizant.customer.app.payload.CustomerDto;

public interface CustomerService {

	// create
	CustomerDto createCustomer(CustomerDto customerDto);

	// read
	CustomerDto getCustomerByCustomerId(String customerId);

	// update
	CustomerDto updateCustomer(CustomerDto customerDto, String customerId);

}
