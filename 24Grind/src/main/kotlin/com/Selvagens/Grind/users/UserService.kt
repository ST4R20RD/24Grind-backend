package com.Selvagens.Grind.users

import com.Selvagens.Grind.users.entity.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException


@Service
class UserService(val userRepo: UserRepository) {

    fun getUsers(search: String?): Iterable<UserEntity> = userRepo.findAll()

    fun signupUser(signupUser: SignupUserRequestDTO): UserEntity = userRepo.save(
        signupUser.toEntity()
    )

    fun findByAccount(accountName: String): UserEntity? = userRepo.findByAccountName(accountName)

    fun getUser(id: Long): UserEntity =
        userRepo.findById(id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

    fun updateUser(id: Long, userRequest: UserUpdateRequest): UserEntity {
        val user = getUser(id)
        if (userRequest.username != null) user.username = userRequest.username!!
        if (userRequest.image != null) user.image = userRequest.image!!

        return userRepo.save(user)
    }
}
