package com.Selvagens.Grind.users

import com.Selvagens.Grind.users.entity.UserEntity
import com.Selvagens.Grind.users.entity.UserUpdateRequest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTest {

    @Mock
    lateinit var userRepo: UserRepository

    @InjectMocks
    lateinit var underTest: UserService

    companion object {
        val user1 = UserEntity("Edward Teach", "dark_dark", "picture", 1)
        val user2 = UserEntity("Jewelry Bonny", "age_age?", "image", 2)
        var userList = listOf(user1, user2)
    }

    @Test
    fun `updateUser only changes the properties passed by the userRequest`() {
        val userEntity = UserEntity("Trafalgar Law", "shambles", "jpeg")

        `when`(userRepo.findById(3)).thenReturn(Optional.of(userEntity))
        `when`(userRepo.save(userEntity)).thenReturn(userEntity)
        underTest.updateUser(3, UserUpdateRequest("Traguy", null))

        assertEquals("Traguy", userEntity.username)
        assertNotNull(userEntity.image)
    }

    @Test
    fun `getUsers returns all existing users`() {
        doReturn(userList).`when`(userRepo).findAll()

        val users = underTest.getUsers(null)

        assertEquals(userList.size, users.count())
    }

}