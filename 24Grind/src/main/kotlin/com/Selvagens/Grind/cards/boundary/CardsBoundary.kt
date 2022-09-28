package com.Selvagens.Grind.cards.boundary

import com.Selvagens.Grind.cards.control.CardsControl
import com.Selvagens.Grind.cards.domain.CardDAO
import com.Selvagens.Grind.cards.domain.CardDTO
import com.Selvagens.Grind.cards.domain.Card_IdDTO
import org.springframework.web.bind.annotation.RestController

@RestController
class CardsBoundary(private val cards: CardsControl) : CardsAPI{
    override fun postCard(card: CardDTO): CardDTO {
        return cards.addOne(card.toDAO())
            .let { it.toDTO() }
    }

    override fun getAllCards(category: String?, location: String?): List<Card_IdDTO> {
        if(category.isNullOrEmpty() && location.isNullOrEmpty())
                return cards.getAll().map { it.toIdDTO() }

        if(!category.isNullOrEmpty() && location.isNullOrEmpty())
            return cards.findByCategoryContaining(category).map { it.toIdDTO() }

        if(category.isNullOrEmpty() && !location.isNullOrEmpty())
            return cards.findByLocationContaining(location).map { it.toIdDTO() }

        return cards.findByCategoryContainingAndLocationContaining(category.toString(), location.toString()).map { it.toIdDTO() }
    }

    override fun getCard(cardId: Long): CardDTO = cards.getOne(cardId).toDTO()

}