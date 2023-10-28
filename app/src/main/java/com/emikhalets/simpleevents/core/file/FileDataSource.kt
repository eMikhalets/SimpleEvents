package com.emikhalets.simpleevents.core.file

import android.net.Uri
import com.emikhalets.simpleevents.core.common.extensions.logd
import com.emikhalets.simpleevents.domain.model.EventModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileDataSource @Inject constructor(
    private val backupManager: AppBackupManager,
) {

    suspend fun importEvents(uri: Uri): List<EventModel> {
        logd("importEvents: $uri")
        return backupManager.import(uri)
    }

    suspend fun exportEvents(uri: Uri, events: List<EventModel>): Boolean {
        logd("exportEvents: $uri, $events")
        return backupManager.export(uri, events)
    }
}
