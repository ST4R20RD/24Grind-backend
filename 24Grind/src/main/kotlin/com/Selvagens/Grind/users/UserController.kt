package com.Selvagens.Grind.users

import com.Selvagens.Grind.users.entity.*
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/v1/users")
class UserController(private val userService: UserService) {
    private final val HOUR = 1000 * 60 * 60
    private final val DAY = 24 * HOUR
    @Value("\${jwt_name}")
    private val jwtName: String = ""
    @Value("\${jwt_secret}")
    private val jwtSecret: String = ""

    @PostMapping("/signup")
    fun signupUser(@RequestBody userRequest: SignupUserRequest) = userService.signnupUser(userRequest).toDTO()

    @PostMapping("/login")
    fun loginUser(@RequestBody loginUser: LoginUserRequest, response: HttpServletResponse): ResponseEntity<Any> {
        val user = userService.findByAccount(loginUser.accountName) ?: return ResponseEntity.badRequest()
            .body("User with account name ${loginUser.accountName} was not found")

        if (!user.comparePassword(loginUser.password)) {
            return ResponseEntity.badRequest().body("Invalid password")
        }

        val issuer = user.id.toString()

        val jwt = Jwts.builder().setIssuer(issuer).setExpiration(Date(System.currentTimeMillis() + DAY))
            .signWith(SignatureAlgorithm.HS512, jwtSecret).compact()

        val cookie = Cookie(jwtName, jwt)
        cookie.isHttpOnly = true

        response.addCookie(cookie)

        return ResponseEntity.ok(user)
    }

    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any> {
        val cookie = Cookie(jwtName, "")
        cookie.maxAge = 0

        response.addCookie(cookie)

        return ResponseEntity.ok("Logged out!")
    }

    @GetMapping
    fun getUsers(@CookieValue("\${jwt_name}") jwt: String?, @RequestParam search: String?): ResponseEntity<Any> {
        if (jwt == null) {
            return ResponseEntity.status(401).body("Must be authenticated!")
        }
        return ResponseEntity.ok(userService.getUsers(search).map(UserEntity::toDTO))
    }


    @GetMapping("/{id}")
    fun getUser(@CookieValue("\${jwt_name}") jwt: String?, @PathVariable id: Long): ResponseEntity<Any> {
        if (jwt == null) {
            return ResponseEntity.status(401).body("Must be authenticated!")
        }
        return ResponseEntity.ok(userService.getUser(id).toDTO())
    }

    @PatchMapping("/{id}")
    fun updateUser(
        @CookieValue("\${jwt_name}") jwt: String?,
        @PathVariable id: Long,
        @RequestBody userRequest: UserUpdateRequest
    ): ResponseEntity<Any> {
        if (jwt == null) {
            return ResponseEntity.status(401).body("Must be authenticated!")
        }

        val client = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).body
        if (client.issuer.toLong() != id) {
            return ResponseEntity.status(401).body("User not allowed to edit other users!")
        }

        val updatedUser = userService.updateUser(id, userRequest).toDTO()

        return ResponseEntity.ok(updatedUser)
    }
}
