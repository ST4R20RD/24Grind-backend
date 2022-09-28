package com.Selvagens.Grind.group.control

import com.Selvagens.Grind.group.api.GroupDTO
import com.Selvagens.Grind.group.domain.GroupDAO
import com.Selvagens.Grind.group.domain.GroupRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class GroupController (private val groups: GroupRepository) {
/*
 Exceptions are being handled in here, not trowing correct responses do API and web application
 */
    fun getAll(): List<GroupDAO> {
        return groups.findAll().toList()
    }

    fun findByNameContaining(contains: String) : List<GroupDAO> = groups.findByNameContaining(contains)

    fun getOne(id: Long): GroupDAO = groups.findById(id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

    fun addOne(group: GroupDAO): GroupDAO = groups.save(group)

    fun editOne(groupId: Long, newGroup: GroupDAO): GroupDAO {
        val optionalGroup = groups.findById(groupId)

        if(!optionalGroup.isPresent) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        val group = optionalGroup.get()

        val toSaveGroup = updateGroupNonNULLValues(group, newGroup)

        return groups.save(toSaveGroup)

    }

    private fun updateGroupNonNULLValues(group: GroupDAO, newGroup: GroupDAO) : GroupDAO{
        //objectivo desta função é manter o idno group
        if(newGroup.name.isNotEmpty())
            group.name = newGroup.name

        if(newGroup.owner > 0)//cant be null
            group.owner = newGroup.owner

        if(newGroup.members.isNotEmpty())
            group.members = newGroup.members

        if(newGroup.cards.isNotEmpty())
            group.cards = newGroup.cards

        if(newGroup.categories.isNotEmpty())
            group.categories = newGroup.categories
        return group
    }
}