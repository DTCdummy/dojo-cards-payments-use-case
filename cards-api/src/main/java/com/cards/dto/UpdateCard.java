package com.cards.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCard {
	
	@NotEmpty(message = "Card type can't be empty")
	private String cardType;
	@NotEmpty(message = "Date can't be empty")
	@Pattern(regexp = "^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d$", message = "Date must be MM/dd/YYYY")
	private String expirationDate;

}

