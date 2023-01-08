package com.grind.users

import com.grind.Application.Companion.jwtName
import com.grind.cards.*
import com.grind.errors.ErrorCode
import com.grind.errors.ErrorResponse
import com.grind.users.*
import com.grind.utils.JwtUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val userService: UserService, private val cardService: CardsService, private val jwtUtils: JwtUtils
) {

    @PostMapping("/signup")
    fun signupUser(@RequestBody userRequest: SignupUserRequestDTO) = ResponseEntity.ok(
        userService.signupUser(userRequest).toDTO()
    )

    @PostMapping("/login")
    fun loginUser(@RequestBody loginUser: LoginUserRequestDTO, response: HttpServletResponse): ResponseEntity<Any> {
        val user = userService.findByAccount(loginUser.accountName) ?: return ResponseEntity.badRequest()
            .body(ErrorResponse(HttpStatus.BAD_REQUEST.value(), ErrorCode.WRONG_USER_OR_PASSWORD))

        if (!user.comparePassword(loginUser.password)) {
            return ResponseEntity.badRequest()
                .body(ErrorResponse(HttpStatus.BAD_REQUEST.value(), ErrorCode.WRONG_USER_OR_PASSWORD))
        }

        val cookie = jwtUtils.generateCookie(user.id.toString())

        response.addCookie(cookie)

        return ResponseEntity.ok(user.toDTO())
    }

    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any> {
        val cookie = Cookie(jwtName, "")
        cookie.maxAge = 0
        cookie.path = ""

        response.addCookie(cookie)

        return ResponseEntity.ok().build()
    }

    @GetMapping
    fun getUsers(@CookieValue(jwtName) jwt: String?, @RequestParam search: String?): ResponseEntity<Any> {
        if (jwt == null) {
            return ResponseEntity.badRequest()
                .body(ErrorResponse(HttpStatus.BAD_REQUEST.value(), ErrorCode.USER_NOT_AUTHENTICATED))
        }
        return ResponseEntity.ok(userService.getUsers(search).map(UserEntity::toDTO))
    }


    @GetMapping("/{id}")
    fun getUser(@CookieValue(jwtName) jwt: String?, @PathVariable("id") id: Long): ResponseEntity<Any> {
        if (jwt == null) {
            return ResponseEntity.badRequest()
                .body(ErrorResponse(HttpStatus.BAD_REQUEST.value(), ErrorCode.USER_NOT_AUTHENTICATED))
        }
        return ResponseEntity.ok(userService.getUser(id).toDTO())
    }

    @PostMapping("/{id}")
    fun updateUser(
        @CookieValue(jwtName) jwt: String?, @PathVariable("id") id: Long, @RequestBody userRequest: UserUpdateRequest
    ): ResponseEntity<Any> {
        if (jwt == null) {
            return ResponseEntity.badRequest()
                .body(ErrorResponse(HttpStatus.BAD_REQUEST.value(), ErrorCode.USER_NOT_AUTHENTICATED))
        }

        if (!jwtUtils.isSameUser(id, jwt)) {
            return ResponseEntity.badRequest().body(
                ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(), ErrorCode.CANNOT_EDIT_OTHER_USERS
                )
            )
        }

        val updatedUser = userService.updateUser(id, userRequest).toDTO()

        return ResponseEntity.ok(updatedUser)
    }

    @PostMapping("{id}/cards")
    fun createCard(
        @CookieValue(jwtName) jwt: String?, @PathVariable("id") userId: Long, @RequestBody cardRequest: CardRequestDTO
    ): ResponseEntity<Any> {
        if (jwt == null) {
            return ResponseEntity.badRequest()
                .body(ErrorResponse(HttpStatus.BAD_REQUEST.value(), ErrorCode.USER_NOT_AUTHENTICATED))
        }

        if (!jwtUtils.isSameUser(userId, jwt)) {
            return ResponseEntity.badRequest()
                .body(ErrorResponse(HttpStatus.BAD_REQUEST.value(), ErrorCode.CANNOT_CREATE_CARDS_FOR_OTHER_USERS))
        }

        val user = userService.getUser(userId)
        val card = cardService.createCard(cardRequest.toEntity(user))
        return ResponseEntity.ok(card.toDTO())
    }
}
