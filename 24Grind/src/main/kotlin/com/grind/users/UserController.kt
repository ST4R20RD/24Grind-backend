package com.grind.users

import com.grind.Application.Companion.defaultImage
import com.grind.Application.Companion.jwtName
import com.grind.cards.CardRequestDTO
import com.grind.cards.CardsService
import com.grind.cards.toDTO
import com.grind.cards.toEntity
import com.grind.cards.*
import com.grind.users.*
import com.grind.utils.JwtUtils
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val userService: UserService,
    private val cardService: CardsService,
    private val jwtUtils: JwtUtils
) {


    @PostMapping("/signup")
    fun signupUser(@RequestBody userRequest: SignupUserRequestDTO) = ResponseEntity.ok(
        userService.signupUser
            (userRequest).toDTO()
    )

    @PostMapping("/login")
    fun loginUser(@RequestBody loginUser: LoginUserRequestDTO, response: HttpServletResponse): ResponseEntity<Any> {
        val user = userService.findByAccount(loginUser.accountName) ?: return ResponseEntity.badRequest()
            .body("User with account name ${loginUser.accountName} was not found")

        if (!user.comparePassword(loginUser.password)) {
            return ResponseEntity.badRequest().body("Invalid password")
        }

        val cookie = jwtUtils.generateCookie(user.id.toString())

        response.addCookie(cookie)

        // todo: don't return UserEntity
        return ResponseEntity.ok(user.toDTO())
    }

    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any> {
        val cookie = Cookie(jwtName, "")
        cookie.maxAge = 0
        cookie.path = ""

        response.addCookie(cookie)

        return ResponseEntity.ok("Logged out!")
    }

    @GetMapping
    fun getUsers(@CookieValue(jwtName) jwt: String?, @RequestParam search: String?): ResponseEntity<Any> {
        if (jwt == null) {
            return ResponseEntity.status(401).body("Must be authenticated!")
        }
        return ResponseEntity.ok(userService.getUsers(search).map(UserEntity::toDTO))
    }


    @GetMapping("/{id}")
    fun getUser(@CookieValue(jwtName) jwt: String?, @PathVariable("id") id: Long): ResponseEntity<Any> {
        if (jwt == null) {
            return ResponseEntity.status(401).body("Must be authenticated!")
        }
        return ResponseEntity.ok(userService.getUser(id).toDTO())
    }

    @PostMapping("/{id}")
    fun updateUser(
        @CookieValue(jwtName) jwt: String?,
        @PathVariable("id") id: Long,
        @RequestBody userRequest: UserUpdateRequest
    ): ResponseEntity<Any> {
        if (jwt == null) {
            return ResponseEntity.status(401).body("Must be authenticated!")
        }

        if (!jwtUtils.isSameUser(id, jwt)) {
            return ResponseEntity.status(401).body("User not allowed to edit other users!")
        }

        val updatedUser = userService.updateUser(id, userRequest).toDTO()

        return ResponseEntity.ok(updatedUser)
    }

    @PostMapping("{id}/cards")
    fun createCard(
        @CookieValue(jwtName) jwt: String?, @PathVariable("id") userId: Long, @RequestBody
        cardRequest: CardRequestDTO
    ): ResponseEntity<Any> {
        if (jwt == null) {
            return ResponseEntity.status(401).body("Must be authenticated!")
        }

        if (!jwtUtils.isSameUser(userId, jwt)) {
            return ResponseEntity.status(401).body("User not allowed to create cards for other users!")
        }

        val user = userService.getUser(userId)
        val card = cardService.createCard(cardRequest.toEntity(user))
        return ResponseEntity.ok(card.toDTO())
    }
}
