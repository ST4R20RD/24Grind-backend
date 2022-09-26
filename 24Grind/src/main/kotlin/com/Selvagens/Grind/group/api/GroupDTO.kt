package com.Selvagens.Grind.group.api

import com.Selvagens.Grind.group.domain.GroupDAO

data class GroupDTO(val name: String,
                    val owner: Long,
                    val members: List<Long>,
                    val cards: List<Long>,
                    val categories: List<String>)
{
    //get DAO from DTO, generating id as long 0
    fun toDAO() : GroupDAO = GroupDAO(0, name, owner, members, cards, categories)
}
