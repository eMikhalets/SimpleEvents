package com.emikhalets.simpleevents.presentation.screens.settings

import android.net.Uri
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.domain.entity.database.EventAlarm
import com.emikhalets.simpleevents.domain.usecase.SettingsUseCase
import com.emikhalets.simpleevents.utils.BaseViewModel
import com.emikhalets.simpleevents.utils.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCase: SettingsUseCase,
) : BaseViewModel<SettingsState>() {

    override fun createInitialState(): SettingsState = SettingsState()

    fun resetError() = setState { it.copy(error = null) }
    fun resetImported() = setState { it.copy(imported = false) }
    fun resetExported() = setState { it.copy(exported = false) }

    fun loadAllNotificationsGlobal() {
        launchIO {
            setState { it.copy(loading = true) }
            useCase.loadNotificationsGlobal()
                .onSuccess { result ->
                    result.collectLatest { list ->
                        setState { it.copy(loading = false, eventAlarms = list) }
                    }
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
                            it.copy(error = uiError)
                        } else {
                            it.copy(imported = true)
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
        launchIO {
            useCase.getAllEvents()
                .onSuccess { eventsResult ->
                    useCase.exportEvents(uri, eventsResult)
                        .onSuccess {
                            setState { it.copy(exported = true) }
                        }
                        .onFailure { error ->
                            val uiError = UiString.Message(error.message)
                            setState { it.copy(loading = false, error = uiError) }
                        }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }
}
