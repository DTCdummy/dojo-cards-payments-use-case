package com.payment.model;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
	
	private String cardNumber;
	private String cardType;
	private String expirationDate;
	private String customerId;
	@Size(max = 5, message = "Payment limit exceeded")
	private String amount;

}
