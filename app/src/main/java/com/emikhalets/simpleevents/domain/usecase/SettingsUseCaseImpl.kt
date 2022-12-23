package com.emikhalets.simpleevents.domain.usecase

import android.net.Uri
import com.emikhalets.simpleevents.data.repository.AppRepository
import com.emikhalets.simpleevents.data.repository.DatabaseRepository
import com.emikhalets.simpleevents.domain.entity.database.EventAlarm
import com.emikhalets.simpleevents.domain.entity.database.EventEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsUseCaseImpl @Inject constructor(
    private val appRepository: AppRepository,
    private val databaseRepo: DatabaseRepository,
) : SettingsUseCase {

    override suspend fun loadNotificationsGlobal(): Result<Flow<List<EventAlarm>>> {
        return databaseRepo.getAllNotifGlobal()
    }

    override suspend fun updateNotificationsGlobal(entity: EventAlarm): Result<Int> {
        return databaseRepo.updateNotifGlobal(entity)
    }

    override suspend fun getAllEvents(): Result<List<EventEntity>> {
        return databaseRepo.getAllEvents()
    }

    override suspend fun importEvents(uri: Uri): Result<List<EventEntity>> {
        return appRepository.importEvents(uri)
    }

    override suspend fun exportEvents(uri: Uri, events: List<EventEntity>): Result<Boolean> {
        return appRepository.exportEvents(uri, events)
    }
}
