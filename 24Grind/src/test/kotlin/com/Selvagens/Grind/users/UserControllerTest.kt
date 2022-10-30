package com.Selvagens.Grind.users

import com.Selvagens.Grind.users.entity.UserEntity
import com.Selvagens.Grind.users.entity.toDTO
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.server.ResponseStatusException

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    lateinit var mvc:MockMvc

    @MockBean
    lateinit var users: UserService

    companion object {
        val user1 = UserEntity("Eustass Kidd", "magnet_magnet", "picture", 1)
    }

    @Test
    fun `GET user by id should return the correct user with status 200`() {
        `when`(users.getUser(1)).thenReturn(user1)

        mvc.perform(get("/v1/users/1"))
            .andExpect(status().isOk)
            .andExpect(content().string(Json.encodeToString(user1.toDTO())))
            .andReturn()
    }

    @Test
    fun `GET non-existing user should return status 404`() {
        `when`(users.getUser(anyLong())).thenThrow(ResponseStatusException(HttpStatus.NOT_FOUND))

        mvc.perform(get("/v1/users/3"))
            .andExpect(status().isNotFound)
            .andReturn()
    }
}