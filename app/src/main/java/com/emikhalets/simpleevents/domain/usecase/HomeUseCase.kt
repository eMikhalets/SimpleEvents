package com.emikhalets.simpleevents.domain.usecase

import com.emikhalets.simpleevents.domain.entity.database.EventEntity

interface HomeUseCase {

    suspend fun loadAllEvents(): Result<List<EventEntity>>
}
