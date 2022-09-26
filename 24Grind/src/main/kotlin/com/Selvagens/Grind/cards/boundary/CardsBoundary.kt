package com.Selvagens.Grind.cards.boundary

import com.Selvagens.Grind.cards.control.CardsControl
import com.Selvagens.Grind.cards.domain.CardDAO
import com.Selvagens.Grind.cards.domain.CardDTO
import org.springframework.web.bind.annotation.RestController

@RestController
class CardsBoundary(private val cards: CardsControl) : CardsAPI{
    override fun postCard(card: CardDTO): CardDTO {
        return cards.addOne(card.toDAO())
            .let { it.toDTO() }
    }

    override fun getAllCards(search: String): List<CardDTO> {
        if(search.isNullOrEmpty())
            return cards.getAll().map { it.toDTO() }

        return cards.findByNameContaining(search).map { it.toDTO() }
    }

    override fun getCard(cardId: Long): CardDTO = cards.getOne(cardId).toDTO()
/*
    private fun convertCardDTOtoDAO(cardDto: CardDTO) : CardDAO = CardDAO(
        0,
        cardDto.authorId,
        cardDto.name,
        cardDto.image,
        cardDto.duration,
        cardDto.date,
        cardDto.tags,
        cardDto.description,
        cardDto.location,
        cardDto.category,
        cardDto.group,
        cardDto.participants
    )

    private fun convertCardDAOtoDTO(cardDao: CardDAO) : CardDTO = CardDTO(
        cardDao.authorId,
        cardDao.name,
        cardDao.image,
        cardDao.duration,
        cardDao.date,
        cardDao.tags,
        cardDao.description,
        cardDao.location,
        cardDao.category,
        cardDao.group,
        cardDao.participants
    )*/

}