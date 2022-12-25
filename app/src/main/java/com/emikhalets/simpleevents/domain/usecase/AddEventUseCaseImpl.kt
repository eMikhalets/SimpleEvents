package com.emikhalets.simpleevents.domain.usecase

import com.emikhalets.simpleevents.domain.entity.database.EventEntity
import com.emikhalets.simpleevents.domain.repository.DatabaseRepository
import javax.inject.Inject

class AddEventUseCaseImpl @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) : AddEventUseCase {

    override suspend fun saveEvent(entity: EventEntity): Result<Long> {
        return databaseRepo.insertEvent(entity)
    }
}
