package com.emikhalets.simpleevents.data.mapper

import com.emikhalets.simpleevents.data.database.entity.GroupDb
import com.emikhalets.simpleevents.domain.entity.GroupEntity

object GroupMapper {

    fun mapDbToEntity(entity: GroupDb): GroupEntity = GroupEntity(
        id = entity.id,
        name = entity.name,
        isAlarmsEnabled = entity.isAlarmsEnabled
    )

    fun mapEntityToDb(entity: GroupEntity): GroupDb = GroupDb(
        id = entity.id,
        name = entity.name,
        isAlarmsEnabled = entity.isAlarmsEnabled
    )

    fun mapDbListToList(list: List<GroupDb>): List<GroupEntity> = list.map {
        mapDbToEntity(it)
    }

    fun mapListToDbList(list: List<GroupEntity>): List<GroupDb> = list.map {
        mapEntityToDb(it)
    }
}
