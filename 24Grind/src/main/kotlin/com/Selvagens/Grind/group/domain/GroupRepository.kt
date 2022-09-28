package com.Selvagens.Grind.group.domain

import org.springframework.data.repository.CrudRepository

interface GroupRepository : CrudRepository<GroupDAO, Long> {
    fun findByNameContaining(contains: String) : List<GroupDAO>
}