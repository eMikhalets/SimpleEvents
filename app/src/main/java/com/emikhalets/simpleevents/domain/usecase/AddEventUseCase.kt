package com.emikhalets.simpleevents.domain.usecase

import com.emikhalets.simpleevents.data.database.EventEntityDB

interface AddEventUseCase {

    suspend fun saveEvent(entity: EventEntityDB): Result<Long>
}