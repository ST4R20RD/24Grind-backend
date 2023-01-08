package com.grind.users

import com.grind.cards.*
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.grind.Application.Companion.defaultImage
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

    @Column(name = "email", unique = true)
    var email: String = ""

    @Column(name = "password")
    @JsonIgnore
    var password: String = ""
        set(value) {
            val passwordEncoder = BCryptPasswordEncoder()
            field = passwordEncoder.encode(value)
        }

    @Column(name = "image")
    var image: String = ""

    // check looping generation: user has cards, cards have a user, user has cards
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "author")
    var cards: MutableList<CardEntity> = mutableListOf()

    @Id
    @GeneratedValue
    var id: Long = 0

    fun comparePassword(password: String): Boolean = BCryptPasswordEncoder().matches(password, this.password)
}

fun UserEntity.toDTO() = UserDTO(username, accountName, email, image, cards.map(CardEntity::toUserDTO)
    .toMutableList(), id)
fun UserEntity.toCardDTO() = CardUserDTO(username, accountName, email, image, id)

@Serializable
data class UserDTO(
    val username: String,
    val accountName: String,
    val email: String,
    val image: String,
    val cards: List<UserCardDTO>,
    val id: Long?
)

@Serializable
data class CardUserDTO(
    val username: String,
    val accountName: String,
    val email: String,
    val image: String,
    val id: Long?
)

data class SignupUserRequestDTO(
    @JsonProperty("username")
    var username: String,
    @JsonProperty("accountName")
    var accountName: String,
    @JsonProperty("password")
    var password: String,
    @JsonProperty("email")
    var email: String
)

fun SignupUserRequestDTO.toEntity(): UserEntity {
    val user = UserEntity()
    user.username = username
    user.accountName = accountName
    user.password = password
    user.image = defaultImage
    user.email = email
    return user
}

data class LoginUserRequestDTO(
    @JsonProperty("accountName")
    var accountName: String,
    @JsonProperty("password")
    var password: String
)

data class UserUpdateRequest(
    @JsonProperty(value = "username", required = false)
    val username: String?,
    @JsonProperty(value = "image", required = false)
    val image: String?
)
