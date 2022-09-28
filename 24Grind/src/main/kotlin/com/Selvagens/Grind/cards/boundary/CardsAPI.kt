package com.Selvagens.Grind.cards.boundary

import com.Selvagens.Grind.cards.domain.CardDAO
import com.Selvagens.Grind.cards.domain.CardDTO
import com.Selvagens.Grind.cards.domain.Card_IdDTO
import org.springframework.web.bind.annotation.*

@RequestMapping("/v1/cards")
interface CardsAPI {

    @PostMapping
    fun postCard(@RequestBody card: CardDTO) : CardDTO

    @GetMapping()
    fun getAllCards(@RequestParam category: String?, @RequestParam location: String?) : List<Card_IdDTO>

    @GetMapping("/{cardId}")
    fun getCard(@PathVariable cardId: Long) : CardDTO
}