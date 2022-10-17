package com.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {
	
	private String cardNumber;
	private String cardType;
	private String expirationDate;
	private String fkCustomerId;

}
