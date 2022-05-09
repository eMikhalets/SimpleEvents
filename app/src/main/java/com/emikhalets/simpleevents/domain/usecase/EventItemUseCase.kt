package com.emikhalets.simpleevents.domain.usecase

import com.emikhalets.simpleevents.data.database.EventEntityDB

interface EventItemUseCase {

    suspend fun loadEvent(eventId: Long): Result<EventEntityDB>
}