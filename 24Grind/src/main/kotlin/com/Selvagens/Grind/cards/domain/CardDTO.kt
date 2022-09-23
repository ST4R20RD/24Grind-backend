package com.Selvagens.Grind.cards.domain

data class CardDTO(
    val authorId: Long, //OneToMany
    val name: String,
    val image: String,
    val duration: String,
    val date: String,
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
