package com.Selvagens.Grind.cards.boundary

import com.Selvagens.Grind.cards.domain.CardDTO
import org.springframework.web.bind.annotation.*

@RequestMapping("/cards")
interface CardsAPI {

    @PostMapping
    fun postCard(@RequestBody card: CardDTO) : CardDTO

    @GetMapping()
    fun getAllCards(@RequestParam search: String) : List<CardDTO>
}