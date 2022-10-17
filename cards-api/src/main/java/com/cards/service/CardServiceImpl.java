package com.cards.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cards.exception.ResourceAlreadyExistsException;
import com.cards.model.Card;
import com.cards.repository.CardRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CardServiceImpl implements CardService {
	
	@Autowired
	private CardRepository cardRepository;

	@Override
	public Card saveCard(Card card) {
		if(cardRepository.existsById(card.getCardNumber())) {
			log.debug("Error occur while saving card with number: {}", card.getCardNumber());
			throw new ResourceAlreadyExistsException("Card", "cardNumber", card.getCardNumber());
		}
		return cardRepository.save(card);
	}

	@Override
	public Card updateCard(String cardNumber, String cardType, String expirationDate) {
		Card card = cardRepository.findById(cardNumber).get();
		card.setCardType(cardType);
		card.setExpirationDate(expirationDate);
		return cardRepository.save(card);
	}

	@Override
	public Card getCard(String cardNumber) {
		return cardRepository.findById(cardNumber).get();
	}

}
