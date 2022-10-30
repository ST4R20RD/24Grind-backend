package com.Selvagens.Grind.cards

import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus

@Service
class CardsService(private val cards: CardsRepository) {

    fun createCard(card: CardEntity): CardEntity = cards.save(card)

    fun getAll(): List<CardEntity> = cards.findAll().toList()

    fun getCard(id: Long): CardEntity =
        cards.findById(id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

    fun findByCategoryContaining(category: String): List<CardEntity> =
        cards.findByCategoryContaining(category).toList()

    fun findByLocationContaining(location: String): List<CardEntity> =
        cards.findByLocationContaining(location).toList()

    fun findByCategoryContainingAndLocationContaining(category: String, location: String): List<CardEntity> =
        cards.findByCategoryContainingAndLocationContaining(category, location).toList()
}