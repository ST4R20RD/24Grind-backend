package com.Selvagens.Grind.group.boundary

import com.Selvagens.Grind.group.api.GroupAPI
import com.Selvagens.Grind.group.api.GroupDTO
import com.Selvagens.Grind.group.control.GroupController
import com.Selvagens.Grind.group.domain.GroupDAO
import org.springframework.web.bind.annotation.RestController

@RestController
class GroupBoundary(val groups: GroupController) : GroupAPI {
    override fun getSearchGroups(search: String?): List<GroupDTO> {

        if(search.isNullOrEmpty())
        return groups.getAll().map { it.toDTO() }

        return groups.findByNameContaining(search).map { it.toDTO() }
    }

    override fun postGroup(group: GroupDTO): GroupDTO {
        return groups.addOne( group.toDAO() ).let { it.toDTO() }
    }

    override fun getGroup(groupId: Long): GroupDTO = groups.getOne(groupId).let{ it.toDTO() }

    override fun putGroup(groupId: Long, group: GroupDTO): GroupDTO =
        groups.editOne( groupId, group.toDAO() ).let { it.toDTO() }

}