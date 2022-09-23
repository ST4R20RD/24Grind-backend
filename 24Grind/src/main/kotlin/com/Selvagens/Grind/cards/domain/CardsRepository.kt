package com.Selvagens.Grind.cards.domain

import org.springframework.data.repository.CrudRepository

interface CardsRepository : CrudRepository<CardDAO, Long>{
    fun findByNameContaining(search: String) : List<CardDAO>
}