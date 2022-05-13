package com.emikhalets.simpleevents.data.repository

import android.net.Uri
import com.emikhalets.simpleevents.data.database.EventEntityDB
import com.emikhalets.simpleevents.data.database.EventsDao
import com.emikhalets.simpleevents.data.database.NotificationGlobal
import com.emikhalets.simpleevents.data.database.NotificationsGlobalDao
import com.emikhalets.simpleevents.utils.AppBackupManager
import com.emikhalets.simpleevents.utils.Mappers
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val eventsDao: EventsDao,
    private val notifGlobalDao: NotificationsGlobalDao,
    private val backupManager: AppBackupManager,
) : DatabaseRepository {

    override suspend fun insertEvent(entity: EventEntityDB): Result<Long> {
        return runCatching { eventsDao.insert(entity) }
    }

    override suspend fun updateEvent(entity: EventEntityDB): Result<Int> {
        return runCatching { eventsDao.update(entity) }
    }

    override suspend fun deleteEvent(entity: EventEntityDB): Result<Int> {
        return runCatching { eventsDao.delete(entity) }
    }

    override suspend fun getAllEvents(): Result<List<EventEntityDB>> {
        return runCatching { eventsDao.getAllEntities() }
    }

    override suspend fun getEntityById(eventId: Long): Result<EventEntityDB> {
        return runCatching { eventsDao.getEntityById(eventId) }
    }

    override suspend fun importEvents(uri: Uri, isOld: Boolean): Result<List<Long>> {
        return runCatching {
            val listDb = if (isOld) {
                val list = backupManager.importOld(uri)
                Mappers.mapFromOldEventsListToEventsDbList(list)
            } else {
                val list = backupManager.import(uri)
                Mappers.mapFromEventsListToEventsDbList(list)
            }

            eventsDao.run {
                drop()
                insert(listDb)
            }
        }
    }

    override suspend fun insertNotifGlobal(entity: NotificationGlobal): Result<Long> {
        return runCatching { notifGlobalDao.insert(entity) }
    }

    override suspend fun insertNotifGlobal(list: List<NotificationGlobal>): Result<List<Long>> {
        return runCatching { notifGlobalDao.insert(list) }
    }

    override suspend fun updateNotifGlobal(entity: NotificationGlobal): Result<Int> {
        return runCatching { notifGlobalDao.update(entity) }
    }

    override suspend fun getAllNotifGlobal(): Result<List<NotificationGlobal>> {
        return runCatching { notifGlobalDao.getAllEntities() }
    }
}