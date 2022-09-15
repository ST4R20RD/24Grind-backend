package com.Selvagens.Grind.group.api

import org.springframework.web.bind.annotation.*

@RequestMapping("groups")
interface GroupAPI {
    @GetMapping
    fun getSearchGroups(@RequestParam search: String): List<GroupDTO>

    @PostMapping
    fun postGroup(@RequestBody group: GroupDTO): GroupDTO

    @GetMapping("{groupId}")
    fun getGroup(@PathVariable groupId: Long): GroupDTO

    @PutMapping("{groupId}")
    fun putGroup(@PathVariable groupId: Long, @RequestBody group:GroupDTO): GroupDTO

}