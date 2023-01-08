package com.grind.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

val frontend = System.getenv()["fe_origin"]

@Configuration
@EnableWebMvc
class WebConfig: WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            // todo: check why fe_origin env variable doesn't work
            .allowedOrigins(frontend)
            .allowCredentials(true)
            .allowedMethods("*")
    }
}