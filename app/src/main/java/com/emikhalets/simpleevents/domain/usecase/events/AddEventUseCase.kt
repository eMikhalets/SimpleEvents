package com.emikhalets.simpleevents.domain.usecase.events

import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.domain.repository.DatabaseRepository
import javax.inject.Inject

class AddEventUseCase @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) {

    suspend operator fun invoke(entity: EventEntity) = when (entity.id) {
        0L -> databaseRepo.insertEvent(entity)
        else -> databaseRepo.updateEvent(entity)
    }
}
