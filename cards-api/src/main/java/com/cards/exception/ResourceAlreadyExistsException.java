package com.cards.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1734092306359143868L;
	
	private String resourceName;
	private String fieldName;
	private String value;
	public ResourceAlreadyExistsException(String resourceName, String fieldName, String value) {
		super(String.format("%s already exists with %s: %s", resourceName, fieldName, value));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.value = value;
	}
	
	

}
