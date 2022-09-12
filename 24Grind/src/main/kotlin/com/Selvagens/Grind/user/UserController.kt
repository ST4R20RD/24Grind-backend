package com.Selvagens.Grind.user

import com.Selvagens.Grind.user.entity.UserEntity
import com.Selvagens.Grind.user.entity.UserCreateRequest
import com.Selvagens.Grind.user.entity.UserUpdateRequest
import com.Selvagens.Grind.user.entity.toDTO
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getUsers(@RequestParam search: String?) = userService.getUsers(search).map(UserEntity::toDTO)

    @PostMapping
    fun createUser(@RequestBody userRequest: UserCreateRequest) = userService.createUser(userRequest).toDTO()

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long) = userService.getUser(id).toDTO()

    @PatchMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody userRequest: UserUpdateRequest) =
        userService.updateUser(id, userRequest).toDTO()
}
