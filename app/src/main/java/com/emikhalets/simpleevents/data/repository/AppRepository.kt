package com.emikhalets.simpleevents.data.repository

import android.net.Uri
import com.emikhalets.simpleevents.domain.entity.database.EventEntity

interface AppRepository {

    suspend fun importEvents(uri: Uri): Result<List<EventEntity>>
    suspend fun exportEvents(uri: Uri, events: List<EventEntity>): Result<Boolean>
}
