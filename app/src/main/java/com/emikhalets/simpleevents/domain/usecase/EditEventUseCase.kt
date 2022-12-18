package com.emikhalets.simpleevents.domain.usecase

import com.emikhalets.simpleevents.domain.entity.database.EventEntity

interface EditEventUseCase {

    suspend fun loadEvent(eventId: Long): Result<EventEntity>
    suspend fun updateEvent(entity: EventEntity): Result<Int>
}
