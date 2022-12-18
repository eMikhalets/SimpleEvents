package com.emikhalets.simpleevents.domain.usecase

import com.emikhalets.simpleevents.domain.entity.database.EventEntity

interface EventItemUseCase {

    suspend fun loadEvent(eventId: Long): Result<EventEntity>
    suspend fun deleteEvent(entity: EventEntity): Result<Int>
}
