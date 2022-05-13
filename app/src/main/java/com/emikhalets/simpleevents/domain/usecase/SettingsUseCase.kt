package com.emikhalets.simpleevents.domain.usecase

import android.net.Uri
import com.emikhalets.simpleevents.data.database.NotificationGlobal

interface SettingsUseCase {

    suspend fun loadNotificationsGlobal(): Result<List<NotificationGlobal>>

    suspend fun updateNotificationsGlobal(entity: NotificationGlobal): Result<Int>

    suspend fun importEvents(uri: Uri, isOld: Boolean): Result<List<Long>>
}