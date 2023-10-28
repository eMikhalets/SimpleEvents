package com.emikhalets.simpleevents.domain.use_case.events

import com.emikhalets.simpleevents.domain.model.EventModel
import com.emikhalets.simpleevents.domain.repository.DatabaseRepository
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) {

    suspend operator fun invoke(entity: EventModel) = databaseRepo.deleteEvent(entity)
}
