package com.Selvagens.Grind.cards

import com.Selvagens.Grind.users.entity.UserDTO
import com.Selvagens.Grind.users.entity.UserEntity
import com.Selvagens.Grind.users.entity.toDTO
import com.Selvagens.Grind.users.entity.toEntity
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable
import javax.persistence.*

@Entity
@Table(name = "cards")
@Serializable
class CardEntity {

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "author_id")
    var author: UserEntity = UserEntity()

    @Column(name = "image")
    var image: String = ""

    @Column(name = "duration")
    var duration: String = ""

    @Column(name = "day")
    var day: String = ""

    @Column(name = "description")
    var description: String = ""

    @Column(name = "location")
    var location: String? = null

    @Column(name = "category")
    var category: String = ""

    @ElementCollection
    @Column(name = "participants")
    var participants: List<Long>? = null

    @Id
    @GeneratedValue
    var id: Long = 0
}

fun CardEntity.toDTO(): CardDTO = CardDTO(
    image,
    duration,
    day,
    description,
    location,
    category,
    participants,
    author.toDTO(),
    id
)

@Serializable
data class CardDTO(
    val image: String,
    val duration: String,
    val day: String,
    val description: String,
    val location: String?,
    val category: String,
    val participants: List<Long>?,
    val author: UserDTO,
    val id: Long?
)

fun CardDTO.toEntity(): CardEntity {
    val card = CardEntity()
    card.author = author.toEntity(author.id)
    card.image = image
    card.duration = duration
    card.day = day
    card.description = description
    card.location = location
    card.category = category
    card.participants = participants
    return card
}

data class CardRequestDTO(
    @JsonProperty("image")
    val image: String,
    @JsonProperty("duration")
    val duration: String,
    @JsonProperty("day")
    val day: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty(value = "location", required = false)
    val location: String?,
    @JsonProperty("category")
    val category: String,
    @JsonProperty(value = "participants", required = false)
    val participants: List<Long>?
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
