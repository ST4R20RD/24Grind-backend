package com.Selvagens.Grind.utils

import com.Selvagens.Grind.Application
import com.Selvagens.Grind.Application.Companion.jwtSecret
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.Cookie

@Component
class JwtUtils {
    companion object {
        private const val HOUR = 1000 * 60 * 60
        private const val DAY = 24 * HOUR
    }


    fun isSameUser(id: Long, jwt: String?) : Boolean {
        val client = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).body
        return client.issuer.toLong() == id
    }

    fun generateCookie(issuer: String): Cookie {
        val jwt = Jwts.builder().setIssuer(issuer).setExpiration(Date(System.currentTimeMillis() + DAY))
            .signWith(SignatureAlgorithm.HS512, jwtSecret).compact()

        val cookie = Cookie(Application.jwtName, jwt)
        cookie.isHttpOnly = true
        cookie.path = "/"

        return cookie
    }
}