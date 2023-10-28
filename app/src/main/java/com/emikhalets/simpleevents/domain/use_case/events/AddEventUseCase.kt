package com.emikhalets.simpleevents.domain.use_case.events

import com.emikhalets.simpleevents.domain.model.EventModel
import com.emikhalets.simpleevents.domain.repository.DatabaseRepository
import javax.inject.Inject

class AddEventUseCase @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) {

    suspend operator fun invoke(entity: EventModel) = when (entity.id) {
        0L -> databaseRepo.insertEvent(entity)
        else -> databaseRepo.updateEvent(entity)
    }
}
