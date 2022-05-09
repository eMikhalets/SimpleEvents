package com.emikhalets.simpleevents.domain.usecase

import com.emikhalets.simpleevents.data.database.EventEntityDB
import com.emikhalets.simpleevents.data.repository.DatabaseRepository
import javax.inject.Inject

class EventItemUseCaseImpl @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) : EventItemUseCase {

    override suspend fun loadEvent(eventId: Long): Result<EventEntityDB> {
        return databaseRepo.getEntityById(eventId)
    }
}