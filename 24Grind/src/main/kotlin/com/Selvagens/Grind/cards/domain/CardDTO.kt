package com.Selvagens.Grind.cards.domain

data class CardDTO(
    val authorId: Long, //OneToMany
    val name: String,
    val image: String,
    val duration: String,
    val day: String,
    val tags: List<String>,
    val description: String,
    val location: String,
    val category: String,
    val group: Long,//OneToMany
    val participants: List<Long>//ManyToMany
) {
    /*
    Note that cardDAO generated using this function has id = 0
     */
    fun toDAO() : CardDAO {
        return CardDAO(
            0,
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

data class Card_IdDTO(
    val cardId: Long,
    val authorId: Long, //OneToMany
    val name: String,
    val image: String,
    val duration: String,
    val day: String,
    val tags: List<String>,
    val description: String,
    val location: String,
    val category: String,
    val group: Long,//OneToMany
    val participants: List<Long>//ManyToMany
) {
    /*
    Note that cardDAO generated using this function has id = 0
     */
    fun toDAO() : CardDAO {
        return CardDAO(
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
