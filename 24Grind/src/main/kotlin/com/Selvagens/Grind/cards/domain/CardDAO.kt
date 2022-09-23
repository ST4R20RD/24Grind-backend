package com.Selvagens.Grind.cards.domain

import javax.persistence.*

@Entity
data class CardDAO(
    @Id @GeneratedValue
    val id: Long,

    val authorId: Long, //OneToMany

    val name: String,

    val image: String,

    val duration: String,

    val date: String,

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
            date,
            tags,
            description,
            location,
            category,
            group,
            participants
        )
    }
}