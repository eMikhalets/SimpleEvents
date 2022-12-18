package com.emikhalets.simpleevents.ui.screens.settings

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.simpleevents.domain.entity.database.NotificationGlobal
import com.emikhalets.simpleevents.domain.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCase: SettingsUseCase,
) : ViewModel() {

    var state by mutableStateOf(SettingsState())
        private set

    fun loadAllNotificationsGlobal() {
        viewModelScope.launch {
            useCase.loadNotificationsGlobal()
                .onSuccess {
                    state = state.copy(
                        notificationsGlobal = it
                    )
                }
                .onFailure {
                    state = state.copy(
                        error = it.localizedMessage ?: ""
                    )
                }
        }
    }

    fun updateNotificationGlobal(notification: NotificationGlobal, enabled: Boolean) {
        viewModelScope.launch {
            val entity = notification.copy(enabled = enabled)
            useCase.updateNotificationsGlobal(entity)
                .onSuccess {
                    loadAllNotificationsGlobal()
                }
                .onFailure {
                    it.printStackTrace()
                    state = state.copy(
                        error = it.localizedMessage ?: ""
                    )
                }
        }
    }

    fun importEvents(uri: Uri, isOld: Boolean = false) {
        viewModelScope.launch {
            useCase.importEvents(uri, isOld)
                .onSuccess {
                    state = if (it.isEmpty()) {
                        state.copy(
                            error = ""
                        )
                    } else {
                        state.copy(
                            imported = it.isNotEmpty()
                        )
                    }
                }
                .onFailure {
                    it.printStackTrace()
                    state = state.copy(
                        error = it.localizedMessage ?: ""
                    )
                }
        }
    }
}