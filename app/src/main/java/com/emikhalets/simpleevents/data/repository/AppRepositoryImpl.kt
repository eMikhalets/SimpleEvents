package com.emikhalets.simpleevents.data.repository

import android.net.Uri
import com.emikhalets.simpleevents.domain.entity.database.EventEntity
import com.emikhalets.simpleevents.domain.repository.AppRepository
import com.emikhalets.simpleevents.utils.AppBackupManager
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val backupManager: AppBackupManager,
) : AppRepository {

    override suspend fun importEvents(uri: Uri): Result<List<EventEntity>> {
        return runCatching { backupManager.import(uri) }
    }

    override suspend fun exportEvents(uri: Uri, events: List<EventEntity>): Result<Boolean> {
        return runCatching { backupManager.export(uri, events) }
    }
}
