package com.cognizant.customer.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {

	@Id
	private String customerId;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	private String password;
	private String name;
	private int age;
	private String address;
	private String phone;
	
}
