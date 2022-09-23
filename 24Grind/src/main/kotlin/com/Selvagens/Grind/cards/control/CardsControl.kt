package com.Selvagens.Grind.cards.control

import com.Selvagens.Grind.cards.domain.CardDAO
import com.Selvagens.Grind.cards.domain.CardsRepository
import org.springframework.stereotype.Service

@Service
class CardsControl(private val cards: CardsRepository) {

    fun addOne(card: CardDAO) : CardDAO = cards.save(card)

    fun getAll() : List<CardDAO> = cards.findAll().toList()

    fun findByNameContaining(search: String) : List<CardDAO> = cards.findByNameContaining(search).toList()
}