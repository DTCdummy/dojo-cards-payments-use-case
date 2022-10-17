package com.cognizant.customer.app.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CustomerDto {

	@Email(message = "Invalid Email Address")
	private String email;
	
	private String customerId;
	
	@NotEmpty
	@Size(min = 5, max = 20, message = "Password should be of length 5-20 characters")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	@NotEmpty
	@Size(max = 20, message = "Name length cannot exceed 20 characters")
	private String name;
	
	@Max(value = 99, message = "Age should be of length 2")
	@Min(value = 10, message = "Age should be of length 2")
	private int age;
	
	@NotEmpty
	@Size(max = 100, message = "Customer Address length should be less than 100 characters")
	private String address;
	
	
	@Size(min = 10, max = 10, message = "Contact number length should be of 10 characters")
	private String phone;
	
}


