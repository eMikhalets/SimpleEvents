package com.emikhalets.simpleevents.data.repository

import android.net.Uri
import com.emikhalets.simpleevents.data.database.EventsDao
import com.emikhalets.simpleevents.data.database.NotificationsGlobalDao
import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.domain.entity.NotificationGlobal
import com.emikhalets.simpleevents.utils.AppBackupManager
import com.emikhalets.simpleevents.utils.Mappers
import timber.log.Timber
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val eventsDao: EventsDao,
    private val notifGlobalDao: NotificationsGlobalDao,
    private val backupManager: AppBackupManager,
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

    override suspend fun importEvents(uri: Uri, isOld: Boolean): Result<List<Long>> {
        return runCatching {
            val listDb = if (isOld) {
                val list = backupManager.importOld(uri)
                Timber.d("Converted old list = $list")
                Mappers.mapFromOldEventsListToEventsDbList(list)
            } else {
                val list = backupManager.import(uri)
                Timber.d("Converted list = $list")
                list
            }

            Timber.d("Mapped list = $listDb")

            eventsDao.run {
                drop()
                insert(listDb)
            }
        }
    }

    /** Notifications Global Dao */

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