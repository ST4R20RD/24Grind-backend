package com.Selvagens.Grind.group.control

import com.Selvagens.Grind.group.api.GroupDTO
import com.Selvagens.Grind.group.domain.GroupDAO
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
internal class GroupBoundaryTest {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var groupControllerMock : GroupController

    companion object{

        private val exampleMembers = listOf<Long>(6,7,8,9,66,77,88)
        private val exampleCards = listOf<Long>(1,2,3,4,5,6,7)
        private val exampleCategories = listOf<String>("fisico", "mental", "lavar a roupa", "correr")

        private val exampleGroupDAO1 = GroupDAO(1, "Jardim Lisboa", 3, exampleMembers, exampleCards, exampleCategories)
        private val exampleGroupDAO2 = GroupDAO(2,"Top G crusaders", 2, exampleMembers, exampleCards, exampleCategories)
        private val exampleGroupDAO3 = GroupDAO(3, "life crusaders", 3, exampleMembers, exampleCards, exampleCategories)

        private val listGroupsDAO = listOf<GroupDAO>(exampleGroupDAO1, exampleGroupDAO2, exampleGroupDAO3)
        private val listCrusadersGroupDAO = listOf<GroupDAO>(exampleGroupDAO2, exampleGroupDAO3)

        private val exampleGroupDTO1 = GroupDTO( "Jardim Lisboa", 3, exampleMembers, exampleCards, exampleCategories)
        private val exampleGroupDTO2 = GroupDTO("Top G crusaders", 2, exampleMembers, exampleCards, exampleCategories)
        private val exampleGroupDTO3 = GroupDTO( "life crusaders", 3, exampleMembers, exampleCards, exampleCategories)

        private val mapper = ObjectMapper()

        //

        private val listGroupsDTO = listOf<GroupDTO>(exampleGroupDTO1, exampleGroupDTO2, exampleGroupDTO3)
        private var listInJsonAllGroups = mapper.writeValueAsString(listGroupsDTO)

        private val listCrusadersGroupDTO = listOf<GroupDTO>(exampleGroupDTO2, exampleGroupDTO3)
        private var listInJsonCrusadersGroups = mapper.writeValueAsString(listCrusadersGroupDTO)




    }

    @Test
    fun `Test getAll`() {
        Mockito.`when`(groupControllerMock.getAll()).thenReturn(listGroupsDAO)

        mvc.perform(get("/groups"))
            .andExpect(status().isOk)
            .andExpect(content().string(listInJsonAllGroups))

    }

    @Test
    fun `Test getAll with a string restrain`() {
        Mockito.`when`(groupControllerMock.getAll()).thenReturn(listGroupsDAO)

        Mockito.`when`(groupControllerMock.findByNameContaining("crusaders")).thenReturn(listCrusadersGroupDAO)


        mvc.perform(get("/groups?search=crusaders"))
            .andExpect(status().isOk)
            .andExpect(content().string(listInJsonCrusadersGroups))

    }
}