package com.grind.cards

import com.grind.Application.Companion.jwtName
import com.grind.errors.ErrorCode
import com.grind.errors.ErrorResponse
import org.springframework.http.HttpStatus
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
            return ResponseEntity.badRequest()
                .body(ErrorResponse(HttpStatus.BAD_REQUEST.value(), ErrorCode.USER_NOT_AUTHENTICATED))
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
            return ResponseEntity.badRequest()
                .body(ErrorResponse(HttpStatus.BAD_REQUEST.value(), ErrorCode.USER_NOT_AUTHENTICATED))
        }

        return ResponseEntity.ok(cards.getCard(cardId).toDTO())
    }

}