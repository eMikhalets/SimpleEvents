package com.emikhalets.simpleevents.data.repository

import com.emikhalets.simpleevents.data.database.EventAlarmsDao
import com.emikhalets.simpleevents.data.database.EventsDao
import com.emikhalets.simpleevents.domain.entity.database.EventAlarm
import com.emikhalets.simpleevents.domain.entity.database.EventEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val eventsDao: EventsDao,
    private val eventAlarmsDao: EventAlarmsDao,
) : DatabaseRepository {

    /** Events Dao */

    override suspend fun insertEvent(entity: EventEntity): Result<Long> {
        return runCatching { eventsDao.insert(entity) }
    }

    override suspend fun updateEvent(entity: EventEntity): Result<Int> {
        return runCatching { eventsDao.update(entity) }
    }

    override suspend fun deleteEvent(entity: EventEntity): Result<Int> {
        return runCatching { eventsDao.delete(entity) }
    }

    override suspend fun getAllEvents(): Result<List<EventEntity>> {
        return runCatching { eventsDao.getAllEntities() }
    }

    override suspend fun getEntityById(eventId: Long): Result<EventEntity> {
        return runCatching { eventsDao.getEntityById(eventId) }
    }

    /** Notifications Global Dao */

    override suspend fun insertNotifGlobal(entity: EventAlarm): Result<Long> {
        return runCatching { eventAlarmsDao.insert(entity) }
    }

    override suspend fun insertNotifGlobal(list: List<EventAlarm>): Result<List<Long>> {
        return runCatching { eventAlarmsDao.insert(list) }
    }

    override suspend fun updateNotifGlobal(entity: EventAlarm): Result<Int> {
        return runCatching { eventAlarmsDao.update(entity) }
    }

    override suspend fun getAllNotifGlobal(): Result<Flow<List<EventAlarm>>> {
        return runCatching { eventAlarmsDao.getAllFlow() }
    }
}
