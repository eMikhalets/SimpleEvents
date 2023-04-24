package com.emikhalets.simpleevents.domain.usecase

import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.domain.repository.DatabaseRepository
import javax.inject.Inject

class HomeUseCaseImpl @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) : HomeUseCase {

    override suspend fun loadAllEvents(): Result<List<EventEntity>> {
        return databaseRepo.getAllEvents()
    }
}
