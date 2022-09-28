package com.Selvagens.Grind.cards.domain

import org.springframework.data.repository.CrudRepository
import javax.xml.stream.Location

interface CardsRepository : CrudRepository<CardDAO, Long>{
    fun findByCategoryContaining(search: String) : List<CardDAO>

    fun findByLocationContaining(location: String) : List<CardDAO>

    fun findByCategoryContainingAndLocationContaining(category: String, location: String) : List<CardDAO>
}