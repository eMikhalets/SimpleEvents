package com.emikhalets.simpleevents.domain.usecase

import android.net.Uri
import com.emikhalets.simpleevents.domain.entity.AlarmEntity
import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.domain.repository.AppRepository
import com.emikhalets.simpleevents.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsUseCaseImpl @Inject constructor(
    private val appRepository: AppRepository,
    private val databaseRepo: DatabaseRepository,
) : SettingsUseCase {

    override suspend fun loadEventsAlarm(): Result<Flow<List<AlarmEntity>>> {
        return databaseRepo.getAllEventsAlarm()
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

    override suspend fun addNotification(notification: AlarmEntity): Result<Long> {
        return databaseRepo.insertEventAlarm(notification)
    }

    override suspend fun updateNotification(notification: AlarmEntity): Result<Int> {
        return databaseRepo.updateEventAlarm(notification)
    }

    override suspend fun deleteNotification(notification: AlarmEntity): Result<Int> {
        return databaseRepo.deleteEventAlarm(notification)
    }
}
