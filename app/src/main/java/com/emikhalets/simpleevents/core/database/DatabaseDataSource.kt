package com.emikhalets.simpleevents.core.database

import com.emikhalets.simpleevents.core.common.extensions.logd
import com.emikhalets.simpleevents.core.database.table_alarms.AlarmDb
import com.emikhalets.simpleevents.core.database.table_alarms.AlarmsDao
import com.emikhalets.simpleevents.core.database.table_events.EventDb
import com.emikhalets.simpleevents.core.database.table_events.EventsDao
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class DatabaseDataSource @Inject constructor(
    private val alarmsDao: AlarmsDao,
    private val eventsDao: EventsDao,
) {

    suspend fun isAlarmExist(model: AlarmDb): Boolean {
        logd("isAlarmExist: $model")
        val isNameExist = alarmsDao.isAlarmExist(model.name)
        val isDaysExist = alarmsDao.isAlarmExist(model.days)
        return isNameExist || isDaysExist
    }

    suspend fun insertAlarm(model: AlarmDb): Long {
        logd("insertAlarm: $model")
        return alarmsDao.insert(model)
    }

    suspend fun insertAlarms(list: List<AlarmDb>): List<Long> {
        logd("insertAlarms: $list")
        return alarmsDao.insert(list)
    }

    suspend fun updateAlarm(model: AlarmDb): Int {
        logd("updateAlarm: $model")
        return alarmsDao.update(model)
    }

    suspend fun deleteAlarm(model: AlarmDb): Int {
        logd("deleteAlarm: $model")
        return alarmsDao.delete(model)
    }

    fun getAllAlarms(): Flow<List<AlarmDb>> {
        logd("getAllAlarms")
        return alarmsDao.getAllFlow()
    }

    suspend fun insertEvent(model: EventDb): Long {
        logd("insertEvent: $model")
        return eventsDao.insert(model)
    }

    suspend fun updateEvent(model: EventDb): Int {
        logd("updateEvent: $model")
        return eventsDao.update(model)
    }

    suspend fun deleteEvent(model: EventDb): Int {
        logd("deleteEvent: $model")
        return eventsDao.delete(model)
    }

    fun getAllEvents(): Flow<List<EventDb>> {
        logd("getAllEvents")
        return eventsDao.getAllFlow()
    }

    suspend fun getEventById(id: Long): EventDb {
        logd("getEventById: $id")
        return eventsDao.getItem(id)
    }
}
