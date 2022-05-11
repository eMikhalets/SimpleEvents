package com.emikhalets.simpleevents.domain.usecase

import com.emikhalets.simpleevents.data.database.NotificationGlobal
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
}