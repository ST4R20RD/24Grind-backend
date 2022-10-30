package com.Selvagens.Grind.users

import com.Selvagens.Grind.users.entity.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserMapperTest {

    @Test
    fun `Map UserCreateRequest to UserBE`() {
        val userCreate = SignupUserRequest("Edward Newgate", "tremor_tremor","gurara", "image")

        val userBE = userCreate.toEntity()

        assertEquals(userCreate.username, userBE.username)
        assertEquals(userCreate.image, userBE.image)
    }

    @Test
    fun `Map UserBE to UserDTO`() {
        val userEntity = UserEntity("Gold Roger", "hehe", "image")

        val userDTO = userEntity.toDTO()

        assertEquals(userEntity.username, userDTO.username)
        assertEquals(userEntity.image, userDTO.image)
    }

    @Test
    fun `Map UserDTO to UserBE`() {
        val userDTO = UserDTO("X Drake", "dragon_dragon","X6", null)

        val userBE = userDTO.toEntity(1)

        assertEquals(userDTO.username, userBE.username)
        assertEquals(userDTO.image, userBE.image)
        assertEquals(1, userBE.id)
    }
}