package com.emikhalets.simpleevents.domain.usecase

import android.net.Uri
import com.emikhalets.simpleevents.domain.entity.NotificationGlobal
import com.emikhalets.simpleevents.data.repository.DatabaseRepository
import javax.inject.Inject

class SettingsUseCaseImpl @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) : SettingsUseCase {

    override suspend fun loadNotificationsGlobal(): Result<List<NotificationGlobal>> {
        return databaseRepo.getAllNotifGlobal()
    }

    override suspend fun updateNotificationsGlobal(entity: NotificationGlobal): Result<Int> {
        return databaseRepo.updateNotifGlobal(entity)
    }

    override suspend fun importEvents(uri: Uri, isOld: Boolean): Result<List<Long>> {
        return databaseRepo.importEvents(uri, isOld)
    }
}