package com.emikhalets.simpleevents.domain.usecase.events

import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.domain.repository.DatabaseRepository
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) {

    suspend operator fun invoke(entity: EventEntity) = databaseRepo.deleteEvent(entity)
}
