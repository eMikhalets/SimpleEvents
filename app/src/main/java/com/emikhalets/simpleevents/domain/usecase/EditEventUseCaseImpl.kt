package com.emikhalets.simpleevents.domain.usecase

import com.emikhalets.simpleevents.data.database.EventEntityDB
import com.emikhalets.simpleevents.data.repository.DatabaseRepository
import javax.inject.Inject

class EditEventUseCaseImpl @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) : EditEventUseCase {

    override suspend fun loadEvent(eventId: Long): Result<EventEntityDB> {
        return databaseRepo.getEntityById(eventId)
    }

    override suspend fun updateEvent(entity: EventEntityDB): Result<Int> {
        return databaseRepo.updateEvent(entity)
    }
}