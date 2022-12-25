package com.emikhalets.simpleevents.data.repository

import com.emikhalets.simpleevents.data.database.EventAlarmsDao
import com.emikhalets.simpleevents.data.database.EventsDao
import com.emikhalets.simpleevents.domain.entity.database.EventAlarm
import com.emikhalets.simpleevents.domain.entity.database.EventEntity
import com.emikhalets.simpleevents.domain.repository.DatabaseRepository
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

    override suspend fun insertEventAlarm(entity: EventAlarm): Result<Long> {
        return runCatching {
            val isNameExist = eventAlarmsDao.isNotificationNameExist(entity.nameEn)
            val isDaysExist = eventAlarmsDao.isNotificationDaysExist(entity.days)
            if (isNameExist || isDaysExist) {
                throw RuntimeException("This name or days already exists")
            } else {
                eventAlarmsDao.insert(entity)
            }
        }
    }

    override suspend fun insertEventAlarm(list: List<EventAlarm>): Result<List<Long>> {
        return runCatching { eventAlarmsDao.insert(list) }
    }

    override suspend fun updateEventAlarm(entity: EventAlarm): Result<Int> {
        return runCatching {
            var isNameExist = false
            var isDaysExist = false
            val oldNotification = eventAlarmsDao.getItem(entity.id)
            if (entity.nameEn != oldNotification.nameEn) {
                isNameExist = eventAlarmsDao.isNotificationNameExist(entity.nameEn)
            }
            if (entity.days != oldNotification.days) {
                isDaysExist = eventAlarmsDao.isNotificationDaysExist(entity.days)
            }
            if (isNameExist || isDaysExist) {
                throw RuntimeException("This name or days already exists")
            } else {
                eventAlarmsDao.update(entity)
            }
        }
    }

    override suspend fun deleteEventAlarm(entity: EventAlarm): Result<Int> {
        return runCatching { eventAlarmsDao.delete(entity) }
    }

    override suspend fun getAllEventsAlarm(): Result<Flow<List<EventAlarm>>> {
        return runCatching { eventAlarmsDao.getAllFlow() }
    }
}
