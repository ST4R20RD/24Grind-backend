package com.Selvagens.Grind.group.control

import com.Selvagens.Grind.group.domain.GroupDAO
import com.Selvagens.Grind.group.domain.GroupRepository
import org.springframework.stereotype.Service

@Service
class GroupController (private val groups: GroupRepository) {
/*
currently Exceptions are being handled in here, not trowing correct responses do API and web application
 */
    fun getAll(): List<GroupDAO> {
        return groups.findAll().toList()
    }

    fun findByNameContaining(contains: String) : List<GroupDAO> = groups.findByNameContaining(contains)

    fun getOne(id: Long): GroupDAO = groups.findById(id).orElseThrow { Exception("Group has no matching id") }

    fun addOne(group: GroupDAO): GroupDAO = groups.save(group)

    fun editOne(groupId: Long, newGroup: GroupDAO): GroupDAO {
        val optionalGroup = groups.findById(groupId)

        if(!optionalGroup.isPresent)
            throw Exception("Group Id doesnt exist in repository")

        val group = optionalGroup.get()

        group.name = newGroup.name
        group.owner = newGroup.owner
        group.members = newGroup.members
        group.cards = newGroup.cards
        group.categories = newGroup.categories

        return groups.save(group)
        /*
        * toda esta ginastica seve para no futuro poder manter o id do editado
        */

    }
}