package com.emikhalets.simpleevents.domain.usecase

import com.emikhalets.simpleevents.data.database.EventEntityDB
import com.emikhalets.simpleevents.data.repository.DatabaseRepository
import javax.inject.Inject

class HomeUseCaseImpl @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) : HomeUseCase {

    override suspend fun loadAllEvents(): Result<List<EventEntityDB>> {
        return databaseRepo.getAllEvents()
    }
}