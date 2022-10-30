package com.Selvagens.Grind.cards

import com.Selvagens.Grind.cards.CardEntity
import org.springframework.data.repository.CrudRepository

interface CardsRepository : CrudRepository<CardEntity, Long>{
    fun findByCategoryContaining(search: String) : List<CardEntity>

    fun findByLocationContaining(location: String) : List<CardEntity>

    fun findByCategoryContainingAndLocationContaining(category: String, location: String) : List<CardEntity>
}