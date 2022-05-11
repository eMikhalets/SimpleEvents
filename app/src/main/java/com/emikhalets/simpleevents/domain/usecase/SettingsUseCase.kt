package com.emikhalets.simpleevents.domain.usecase

import com.emikhalets.simpleevents.data.database.NotificationGlobal

interface SettingsUseCase {

    suspend fun loadNotificationsGlobal(): Result<List<NotificationGlobal>>

    suspend fun updateNotificationsGlobal(entity: NotificationGlobal): Result<Int>
}