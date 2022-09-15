package com.Selvagens.Grind.group.control

import com.Selvagens.Grind.group.api.GroupAPI
import com.Selvagens.Grind.group.api.GroupDTO
import com.Selvagens.Grind.group.boundary.GroupBoundary
import com.Selvagens.Grind.group.domain.GroupDAO
import org.springframework.web.bind.annotation.RestController

@RestController
class GroupControl(val groups: GroupBoundary) : GroupAPI {
    override fun getSearchGroups(search: String): List<GroupDTO> = groups.getAll().map {
            GroupDTO(
                it.name,
                it.owner,
                it.members,//nota que funciona pq sao so long and Strings
                it.cards,
                it.categorias
            )
        }
    /*
    TODO Search not implemented in getALl
     */

    override fun postGroup(group: GroupDTO): GroupDTO {
        return groups.addOne(GroupDAO(0, group.name, group.owner,group.members,group.cards,group.categories))
            .let {
                GroupDTO(
                    it.name,
                    it.owner,
                    it.members,
                    it.cards,
                    it.categorias
                )
            }
        /*
        this method makes groups.addOne(GroupDAO).let(GroupDTO) meaning: sends GroupDAO to boundary and returns
        DTO to the API
         */
    }

    override fun getGroup(groupId: Long): GroupDTO = groups.getOne(groupId)
        .let{
            GroupDTO(
                it.name,
                it.owner,
                it.members,
                it.cards,
                it.categorias
            )
        }

    override fun putGroup(groupId: Long, group: GroupDTO): GroupDTO =
        groups.editOne(groupId, GroupDAO(0, group.name, group.owner,group.members,group.cards,group.categories))
            .let {
                GroupDTO(
                    it.name,
                    it.owner,
                    it.members,
                    it.cards,
                    it.categorias
                )
            }

    /*
    private fun convertGroupDTOtoDAO(groupDTO: GroupDTO): GroupDAO =
        GroupDAO(
            0,
            groupDTO.name,
            groupDTO.owner,
            groupDTO.members,
            groupDTO.cards,
            groupDTO.categories
        )

     */
}