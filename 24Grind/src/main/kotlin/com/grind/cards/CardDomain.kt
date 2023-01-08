package com.grind.cards

import com.fasterxml.jackson.annotation.JsonProperty
import com.grind.users.*
import kotlinx.serialization.Serializable
import javax.persistence.*

@Entity
@Table(name = "cards")
@Serializable
class CardEntity {

    @Column(name = "image")
    var image: String? = null

    @Column(name = "duration")
    var duration: String = ""

    @Column(name = "day")
    var day: String = ""

    @Column(name = "description")
    var description: String = ""

    @Column(name = "location")
    var location: String? = null

    @Column(name = "category")
    var category: String? = ""

    @ElementCollection
    @Column(name = "participants")
    var participants: List<Long>? = null

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "author_id")
    var author: UserEntity = UserEntity()

    @Id
    @GeneratedValue
    var id: Long = 0
}

@Serializable
data class CardDTO(
    val image: String?,
    val duration: String,
    val day: String,
    val description: String,
    val location: String?,
    val category: String?,
    val participants: List<Long>?,
    val author: CardUserDTO,
    val id: Long?
)

@Serializable
data class UserCardDTO(
    val image: String?,
    val duration: String,
    val day: String,
    val description: String,
    val location: String?,
    val category: String?,
    val participants: List<Long>?,
    val id: Long?
)

data class CardRequestDTO(
    @JsonProperty("duration")
    val duration: String,
    @JsonProperty("day")
    val day: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty(value = "image", required = false)
    val image: String?,
    @JsonProperty(value = "location", required = false)
    val location: String?,
    @JsonProperty(value = "category", required = false)
    val category: String?,
    @JsonProperty(value = "participants", required = false)
    val participants: List<Long>?
)

fun CardEntity.toDTO(): CardDTO = CardDTO(
    image,
    duration,
    day,
    description,
    location,
    category,
    participants,
    author.toCardDTO(),
    id
)
fun CardEntity.toUserDTO(): UserCardDTO = UserCardDTO(
    image,
    duration,
    day,
    description,
    location,
    category,
    participants,
    id
)

fun CardRequestDTO.toEntity(author: UserEntity): CardEntity {
    val card = CardEntity()
    card.author = author
    card.image = image
    card.duration = duration
    card.day = day
    card.description = description
    card.location = location
    card.category = category
    card.participants = participants
    return card
}
