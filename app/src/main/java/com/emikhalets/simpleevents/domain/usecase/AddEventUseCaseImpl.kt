package com.emikhalets.simpleevents.domain.usecase

import com.emikhalets.simpleevents.data.database.EventEntityDB
import com.emikhalets.simpleevents.data.repository.DatabaseRepository
import javax.inject.Inject

class AddEventUseCaseImpl @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) : AddEventUseCase {

    override suspend fun saveEvent(entity: EventEntityDB): Result<Long> {
        return databaseRepo.insertEvent(entity)
    }
}