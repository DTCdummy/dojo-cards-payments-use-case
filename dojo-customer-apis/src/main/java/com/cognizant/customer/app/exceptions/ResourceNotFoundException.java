package com.cognizant.customer.app.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -49610231452237094L;
	String resourceName;
	String fieldName;
	String field;

	public ResourceNotFoundException(String resourceName, String fieldName, String field) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, field));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.field = field;
	}
}
