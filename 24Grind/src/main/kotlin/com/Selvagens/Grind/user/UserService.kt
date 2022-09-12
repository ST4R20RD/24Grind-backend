package com.Selvagens.Grind.user

import com.Selvagens.Grind.user.entity.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException


@Service
class UserService(val userRepository: UserRepository) {

    fun getUsers(search: String?): Iterable<UserEntity> = userRepository.findAll()

    fun createUser(userRequest: UserCreateRequest): UserEntity = userRepository.save(userRequest.toEntity())

    fun getUser(id: Long): UserEntity =
        userRepository.findById(id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

    fun updateUser(id: Long, userRequest: UserUpdateRequest): UserEntity {
        val user = getUser(id)
        if (userRequest.username != null) user.username = userRequest.username!!
        if (userRequest.image != null) user.image = userRequest.image!!

        return userRepository.save(user)
    }
}
