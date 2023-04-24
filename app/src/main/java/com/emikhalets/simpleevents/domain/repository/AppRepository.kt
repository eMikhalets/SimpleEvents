package com.emikhalets.simpleevents.domain.repository

import android.net.Uri
import com.emikhalets.simpleevents.domain.entity.EventEntity

interface AppRepository {

    suspend fun importEvents(uri: Uri): Result<List<EventEntity>>
    suspend fun exportEvents(uri: Uri, events: List<EventEntity>): Result<Boolean>
}
