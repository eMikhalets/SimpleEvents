package com.emikhalets.simpleevents.domain.usecase

import com.emikhalets.simpleevents.domain.entity.EventEntity

interface AddEventUseCase {

    suspend fun saveEvent(entity: EventEntity): Result<Long>
}
