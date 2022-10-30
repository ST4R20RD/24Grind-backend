package com.Selvagens.Grind.users

import com.Selvagens.Grind.users.entity.UserEntity
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserEntity, Long> {
    fun findByAccountName(account: String): UserEntity?
}
