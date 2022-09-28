package com.Selvagens.Grind.cards.domain

import javax.persistence.*

@Entity
@Table(name="ActivityCards")
data class CardDAO(
    @Id @GeneratedValue
    val cardId: Long,

    val authorId: Long, //OneToMany

    val name: String,

    val image: String,

    val duration: String,

    val day: String,

    @ElementCollection
    val tags: List<String>,

    val description: String,

    val location: String,

    val category: String,

    val group: Long,//OneToMany

    @ElementCollection
    val participants: List<Long>//ManyToMany

) {
    fun toDTO(): CardDTO{
        return CardDTO(
            authorId,
            name,
            image,
            duration,
            day,
            tags,
            description,
            location,
            category,
            group,
            participants
        )
    }

    fun toIdDTO(): Card_IdDTO{
        return Card_IdDTO(
            cardId,
            authorId,
            name,
            image,
            duration,
            day,
            tags,
            description,
            location,
            category,
            group,
            participants
        )
    }
}