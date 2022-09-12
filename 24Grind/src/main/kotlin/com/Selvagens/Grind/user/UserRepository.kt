package com.Selvagens.Grind.user

import com.Selvagens.Grind.user.entity.UserEntity
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserEntity, Long>