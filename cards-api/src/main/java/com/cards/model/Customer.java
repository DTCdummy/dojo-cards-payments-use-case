package com.cards.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	
	private String customerId;
	private String email;
	private String password;
	private String name;
	private int age;
	private String address;
	private String phone;

}
