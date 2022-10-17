package com.cards.service;

import com.cards.model.Card;

public interface CardService {
	
	Card saveCard(Card card);
	Card updateCard(String cardNumber, String cardType, String expirationDate);
	Card getCard(String cardNumber);

}
