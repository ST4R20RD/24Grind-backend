package com.Selvagens.Grind.group.domain

import com.Selvagens.Grind.group.api.GroupDTO
import javax.persistence.*

@Entity
data class GroupDAO(
    
    @Id @GeneratedValue
    val id: Long,

    var name: String,

    var owner: Long,

    //@ManyToMany
    @ElementCollection//exclusively to replace error of ManyToMany
    var members: List<Long>,//Long -> UserObject

    //@OneToMany
    @ElementCollection//exclusively to replace error of OneToMany
    var cards: List<Long>,//long -> Card object

    @ElementCollection
    var categories: List<String>

)
{
    //convert entity DAO into DTO object, removing de id
    fun toDTO() : GroupDTO = GroupDTO(name, owner, members, cards, categories)
}