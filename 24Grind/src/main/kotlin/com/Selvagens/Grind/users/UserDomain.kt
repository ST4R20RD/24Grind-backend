package com.Selvagens.Grind.users.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.*

@Entity
@Table(name = "users")
@Serializable
class UserEntity {

    @Column(name = "user_name")
    var username: String = ""

    @Column(name = "accountName", unique = true)
    var accountName: String = ""

    @Column(name = "password")
    @JsonIgnore
    var password: String = ""
        set(value) {
            val passwordEncoder = BCryptPasswordEncoder()
            field = passwordEncoder.encode(value)
        }

    @Column(name = "image")
    var image: String = ""

    @Id
    @GeneratedValue
    var id: Long? = null

    fun comparePassword(password: String): Boolean = BCryptPasswordEncoder().matches(password, this.password)
}

fun UserEntity.toDTO() = UserDTO(username, accountName, image, id)

@Serializable
data class UserDTO(
    val username: String,
    val accountName: String,
    val image: String,
    val id: Long?
)

fun UserDTO.toEntity(id: Long): UserEntity {
    val user = UserEntity()
    user.username = username
    user.accountName = accountName
    user.image = image
    user.id = id
    return user
}

data class SignupUserRequest(
    @JsonProperty("username")
    var username: String,
    @JsonProperty("accountName")
    var accountName: String,
    @JsonProperty("password")
    var password: String,
    @JsonProperty("image")
    var image: String
)

fun SignupUserRequest.toEntity(): UserEntity {
    val user = UserEntity()
    user.username = username
    user.accountName = accountName
    user.password = password
    user.image = image
    return user
}

data class LoginUserRequest(
    @JsonProperty("accountName")
    var accountName: String,
    @JsonProperty("password")
    var password: String
)

data class UserUpdateRequest(
    @JsonProperty(value = "username", required = false)
    var username: String? = null,
    @JsonProperty(value = "image", required = false)
    var image: String?
)
