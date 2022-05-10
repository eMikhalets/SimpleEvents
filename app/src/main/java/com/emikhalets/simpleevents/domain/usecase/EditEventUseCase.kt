package com.emikhalets.simpleevents.domain.usecase

import com.emikhalets.simpleevents.data.database.EventEntityDB

interface EditEventUseCase {

    suspend fun loadEvent(eventId: Long): Result<EventEntityDB>

    suspend fun updateEvent(entity: EventEntityDB): Result<Int>
}