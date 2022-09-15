package com.Selvagens.Grind.group.api

data class GroupDTO(val name: String,
                    val owner: Long,
                    val members: List<Long>,
                    val cards: List<Long>,
                    val categories: List<String>)
