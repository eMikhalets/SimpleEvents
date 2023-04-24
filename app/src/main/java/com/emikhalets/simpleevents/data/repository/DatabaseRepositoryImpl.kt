package com.emikhalets.simpleevents.data.repository

import com.emikhalets.simpleevents.data.database.dao.AlarmsDao
import com.emikhalets.simpleevents.data.database.dao.EventsDao
import com.emikhalets.simpleevents.data.mapper.AlarmMapper
import com.emikhalets.simpleevents.data.mapper.EventMapper
import com.emikhalets.simpleevents.domain.entity.AlarmEntity
import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val eventsDao: EventsDao,
    private val eventAlarmsDao: AlarmsDao,
) : DatabaseRepository {

    /** Events Dao */

    override suspend fun insertEvent(entity: EventEntity): Result<Long> {
        return runCatching {
            val dbEntity = EventMapper.mapEntityToDb(entity)
            eventsDao.insert(dbEntity)
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun updateEvent(entity: EventEntity): Result<Int> {
        return runCatching {
            val dbEntity = EventMapper.mapEntityToDb(entity)
            eventsDao.update(dbEntity)
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun deleteEvent(entity: EventEntity): Result<Int> {
        return runCatching {
            val dbEntity = EventMapper.mapEntityToDb(entity)
            eventsDao.delete(dbEntity)
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun getAllEvents(): Result<List<EventEntity>> {
        return runCatching {
            val dbList = eventsDao.getAllEntities()
            EventMapper.mapDbListToList(dbList)
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun getEntityById(eventId: Long): Result<EventEntity> {
        return runCatching {
            val dbEntity = eventsDao.getEntityById(eventId)
            EventMapper.mapDbToEntity(dbEntity)
        }.onFailure { it.printStackTrace() }
    }

    /** Notifications Global Dao */

    override suspend fun insertEventAlarm(entity: AlarmEntity): Result<Long> {
        return runCatching {
            val isNameExist = eventAlarmsDao.isNotificationNameExist(entity.nameEn)
            val isDaysExist = eventAlarmsDao.isNotificationDaysExist(entity.days)
            if (isNameExist || isDaysExist) {
                throw RuntimeException("This name or days already exists")
            } else {
                val dbEntity = AlarmMapper.mapEntityToDb(entity)
                eventAlarmsDao.insert(dbEntity)
            }
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun insertEventAlarm(list: List<AlarmEntity>): Result<List<Long>> {
        return runCatching {
            val dbList = AlarmMapper.mapListToDbList(list)
            eventAlarmsDao.insert(dbList)
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun updateEventAlarm(entity: AlarmEntity): Result<Int> {
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
                val dbEntity = AlarmMapper.mapEntityToDb(entity)
                eventAlarmsDao.update(dbEntity)
            }
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun deleteEventAlarm(entity: AlarmEntity): Result<Int> {
        return runCatching {
            val dbEntity = AlarmMapper.mapEntityToDb(entity)
            eventAlarmsDao.delete(dbEntity)
        }.onFailure { it.printStackTrace() }
    }

    override suspend fun getAllEventsAlarm(): Result<Flow<List<AlarmEntity>>> {
        return runCatching {
            eventAlarmsDao.getAllFlow().map {
                AlarmMapper.mapDbListToList(it)
            }
        }.onFailure { it.printStackTrace() }
    }
}
