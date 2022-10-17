package com.cognizant.customer.app.payload;

import lombok.Data;

@Data
public class CustomerRequest {

	private String customerId;
	private String password;
}
