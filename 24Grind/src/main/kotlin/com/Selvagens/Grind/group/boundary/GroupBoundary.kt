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
        return groups.getAll().map {
            convertGroupDAOtoDTO(it)
        }

        return groups.findByNameContaining(search).map {
            convertGroupDAOtoDTO(it)
        }
    }

    override fun postGroup(group: GroupDTO): GroupDTO {
        return groups.addOne(GroupDAO(0, group.name, group.owner,group.members,group.cards,group.categories))
            .let {
                convertGroupDAOtoDTO(it)
            }
    }

    override fun getGroup(groupId: Long): GroupDTO = groups.getOne(groupId)
        .let{
            convertGroupDAOtoDTO(it)
        }

    override fun putGroup(groupId: Long, group: GroupDTO): GroupDTO =
        groups.editOne(groupId, GroupDAO(0, group.name, group.owner,group.members,group.cards,group.categories))
            .let {
                convertGroupDAOtoDTO(it)
            }


    private fun convertGroupDAOtoDTO(groupDAO: GroupDAO): GroupDTO =
        GroupDTO(
            groupDAO.name,
            groupDAO.owner,
            groupDAO.members,
            groupDAO.cards,
            groupDAO.categories
        )


}