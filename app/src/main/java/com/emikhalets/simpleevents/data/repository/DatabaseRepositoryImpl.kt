package com.emikhalets.simpleevents.data.repository

import com.emikhalets.simpleevents.data.database.EventEntityDB
import com.emikhalets.simpleevents.data.database.EventsDao
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val eventsDao: EventsDao,
) : DatabaseRepository {

    override suspend fun insertEvent(entity: EventEntityDB): Result<Long> {
        return kotlin.runCatching { eventsDao.insert(entity) }
    }

    override suspend fun updateEvent(entity: EventEntityDB): Result<Int> {
        return kotlin.runCatching { eventsDao.update(entity) }
    }

    override suspend fun deleteEvent(entity: EventEntityDB): Result<Int> {
        return kotlin.runCatching { eventsDao.delete(entity) }
    }

    override suspend fun getAllEvents(): Result<List<EventEntityDB>> {
        return kotlin.runCatching { eventsDao.getAllEntities() }
    }
}