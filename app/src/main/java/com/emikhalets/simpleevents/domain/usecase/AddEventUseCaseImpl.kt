package com.emikhalets.simpleevents.domain.usecase

import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.data.repository.DatabaseRepository
import javax.inject.Inject

class AddEventUseCaseImpl @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) : AddEventUseCase {

    override suspend fun saveEvent(entity: EventEntity): Result<Long> {
        return databaseRepo.insertEvent(entity)
    }
}