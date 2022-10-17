package com.cards.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "cards")
public class Card {
	@Id
	@NotEmpty(message = "Card number can't be empty")
	@Size(min = 16, max = 16, message = "Card number must be 16 digits")
	@Column(unique = true)
	private String cardNumber;
	@NotEmpty(message = "Card type can't be empty")
	private String cardType;
	@NotEmpty(message = "Date can't be empty")
	@Pattern(regexp = "^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d$", message = "Date must be MM/dd/YYYY")
	private String expirationDate;
	private String fkCustomerId;
}
