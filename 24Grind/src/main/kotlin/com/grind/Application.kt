package com.grind

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class Application {

    companion object {
        const val jwtName = "jwt"
        const val jwtSecret = "1q2w3e4r"
        const val cloudinaryKey = "585797169536944"
        const val cloudinarySecret = "6pDLL25F4yBNJ9ivIfEZ0PikB4Q"
        const val defaultImage = "https://res.cloudinary.com/dpa5mddyx/image/upload/v1670524849/grind/765-default-avatar_coly6j.png"
    }

}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}