package com.Selvagens.Grind.user.entity

import kotlinx.serialization.Serializable
import javax.persistence.*

@Entity
@Table(name = "users")
@Serializable
data class UserEntity(

    @Column(name = "user_name")
    var username: String,

    @Column(name = "account_name")
    var accountName: String,

    @Column(name = "image")
    var image: String,

    @Id
    @GeneratedValue
    var id: Long? = null
)

fun UserEntity.toDTO() = UserDTO(username, accountName, image, id)

@Serializable
data class UserDTO(
        val username: String,
        val accountName: String,
        val image: String,
        val id: Long?
)

fun UserDTO.toEntity(id: Long) = UserEntity(username, accountName, image, id = id)

data class UserCreateRequest(
        var username: String,
        var accountName: String,
        var password: String,
        var image: String
)

fun UserCreateRequest.toEntity() = UserEntity(username, accountName, image)

data class UserUpdateRequest(
        var username: String?,
        var image: String?
)
