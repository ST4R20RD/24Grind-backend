package com.grind.users

import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserEntity, Long> {
    fun findByAccountName(account: String): UserEntity?
}
