package com.cards.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cards.model.Card;

public interface CardRepository extends JpaRepository<Card, String> {

}
