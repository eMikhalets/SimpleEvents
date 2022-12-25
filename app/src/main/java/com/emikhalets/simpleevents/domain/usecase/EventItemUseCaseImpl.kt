package com.emikhalets.simpleevents.domain.usecase

import com.emikhalets.simpleevents.domain.entity.database.EventEntity
import com.emikhalets.simpleevents.domain.repository.DatabaseRepository
import javax.inject.Inject

class EventItemUseCaseImpl @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) : EventItemUseCase {

    override suspend fun loadEvent(eventId: Long): Result<EventEntity> {
        return databaseRepo.getEntityById(eventId)
    }

    override suspend fun deleteEvent(entity: EventEntity): Result<Int> {
        return databaseRepo.deleteEvent(entity)
    }
}
