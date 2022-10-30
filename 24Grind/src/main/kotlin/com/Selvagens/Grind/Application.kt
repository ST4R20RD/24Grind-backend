package com.Selvagens.Grind

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class Application {

    companion object {
        const val jwtName = "jwt"
        const val jwtSecret = "1q2w3e4r"
    }

}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}