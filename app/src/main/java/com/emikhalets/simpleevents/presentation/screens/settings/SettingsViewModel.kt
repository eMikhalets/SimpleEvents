package com.emikhalets.simpleevents.presentation.screens.settings

import android.net.Uri
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.domain.entity.database.EventAlarm
import com.emikhalets.simpleevents.domain.usecase.SettingsUseCase
import com.emikhalets.simpleevents.utils.BaseViewModel
import com.emikhalets.simpleevents.utils.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCase: SettingsUseCase,
) : BaseViewModel<SettingsState>() {

    override fun createInitialState(): SettingsState = SettingsState()

    fun resetError() = setState { it.copy(error = null) }

    fun loadAllNotificationsGlobal() {
        launchIO {
            setState { it.copy(loading = true) }
            useCase.loadNotificationsGlobal()
                .onSuccess { result ->
                    setState { it.copy(loading = false, eventAlarms = result) }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }

    fun updateNotificationGlobal(notification: EventAlarm, enabled: Boolean) {
        launchIO {
            val entity = notification.copy(enabled = enabled)
            useCase.updateNotificationsGlobal(entity)
                .onSuccess {
                    loadAllNotificationsGlobal()
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }

    fun importEvents(uri: Uri) {
        launchIO {
            useCase.importEvents(uri)
                .onSuccess { result ->
                    setState {
                        if (result.isEmpty()) {
                            val uiError = UiString.Resource(R.string.error_import_events)
                            it.copy(loading = false, error = uiError)
                        } else {
                            it.copy(loading = false, imported = true)
                        }
                    }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }

    fun exportEvents(uri: Uri) {
    }
}