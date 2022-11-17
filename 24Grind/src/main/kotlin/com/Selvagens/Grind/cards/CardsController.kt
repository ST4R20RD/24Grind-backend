package com.Selvagens.Grind.cards

import com.Selvagens.Grind.Application.Companion.jwtName
import com.Selvagens.Grind.users.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/cards")
class CardsController(private val cards: CardsService) {

    @GetMapping
    fun getAllCards(
        @CookieValue(jwtName) jwt: String?,
        @RequestParam category: String?,
        @RequestParam location: String?
    ): ResponseEntity<Any> {
        if (jwt == null) {
            return ResponseEntity.status(401).body("Must be authenticated!")
        }

        if (category.isNullOrEmpty() && location.isNullOrEmpty())
            return ResponseEntity.ok(cards.getAll().map { it.toDTO() })

        if (!category.isNullOrEmpty() && location.isNullOrEmpty())
            return ResponseEntity.ok(cards.findByCategoryContaining(category).map { it.toDTO() })

        if (category.isNullOrEmpty() && !location.isNullOrEmpty())
            return ResponseEntity.ok(cards.findByLocationContaining(location).map { it.toDTO() })

        return ResponseEntity.ok(
            cards.findByCategoryContainingAndLocationContaining(
                category.toString(), location.toString()
            ).map {
                it.toDTO()
            })
    }

    @GetMapping("/{cardId}")
    fun getCard(@CookieValue(jwtName) jwt: String?, @PathVariable cardId: Long): ResponseEntity<Any> {
        if (jwt == null) {
            return ResponseEntity.status(401).body("Must be authenticated!")
        }

        return ResponseEntity.ok(cards.getCard(cardId).toDTO())
    }

}