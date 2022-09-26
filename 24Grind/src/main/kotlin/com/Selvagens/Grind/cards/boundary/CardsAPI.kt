package com.Selvagens.Grind.cards.boundary

import com.Selvagens.Grind.cards.domain.CardDTO
import org.springframework.web.bind.annotation.*

@RequestMapping("/v1/cards")
interface CardsAPI {

    @PostMapping
    fun postCard(@RequestBody card: CardDTO) : CardDTO

    @GetMapping()
    fun getAllCards(@RequestParam search: String) : List<CardDTO>

    @GetMapping("/{cardId}")
    fun getCard(@PathVariable cardId: Long) : CardDTO
}