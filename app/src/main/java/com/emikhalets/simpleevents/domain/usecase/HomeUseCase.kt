package com.emikhalets.simpleevents.domain.usecase

import com.emikhalets.simpleevents.data.database.EventEntityDB

interface HomeUseCase {

    suspend fun loadAllEvents(): Result<List<EventEntityDB>>
}