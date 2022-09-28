package com.Selvagens.Grind.cards.control

import com.Selvagens.Grind.cards.domain.CardDAO
import com.Selvagens.Grind.cards.domain.CardsRepository
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus

@Service
class CardsControl(private val cards: CardsRepository) {

    fun addOne(card: CardDAO) : CardDAO = cards.save(card)

    fun getAll() : List<CardDAO> = cards.findAll().toList()

    fun getOne(cardId: Long) : CardDAO = cards.findById(cardId).orElseThrow{ ResponseStatusException(HttpStatus.NOT_FOUND) }

    fun findByCategoryContaining(category: String) : List<CardDAO> = cards.findByCategoryContaining(category).toList()

    fun findByLocationContaining(location: String) : List<CardDAO> = cards.findByLocationContaining(location).toList()

    fun findByCategoryContainingAndLocationContaining(category: String, location: String) : List<CardDAO> =
        cards.findByCategoryContainingAndLocationContaining(category, location).toList()
}