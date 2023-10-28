package com.emikhalets.simpleevents.domain.use_case.events

import com.emikhalets.simpleevents.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) {

    suspend operator fun invoke() = databaseRepo.getAllEvents()

    suspend operator fun invoke(id: Long) = databaseRepo.getEntityById(id)
}
