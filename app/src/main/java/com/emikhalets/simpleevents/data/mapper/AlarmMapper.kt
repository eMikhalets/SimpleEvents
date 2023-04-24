package com.emikhalets.simpleevents.data.mapper

import com.emikhalets.simpleevents.data.database.entity.AlarmDb
import com.emikhalets.simpleevents.domain.entity.AlarmEntity

object AlarmMapper {

    fun mapDbToEntity(entity: AlarmDb): AlarmEntity = AlarmEntity(
        id = entity.id,
        nameEn = entity.nameEn,
        enabled = entity.enabled,
        days = entity.days
    )

    fun mapEntityToDb(entity: AlarmEntity): AlarmDb = AlarmDb(
        id = entity.id,
        nameEn = entity.nameEn,
        enabled = entity.enabled,
        days = entity.days
    )

    fun mapDbListToList(list: List<AlarmDb>): List<AlarmEntity> = list.map {
        mapDbToEntity(it)
    }

    fun mapListToDbList(list: List<AlarmEntity>): List<AlarmDb> = list.map {
        mapEntityToDb(it)
    }
}
