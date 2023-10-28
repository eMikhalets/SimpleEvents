package com.emikhalets.simpleevents.presentation.screens.settings

import android.net.Uri
import com.emikhalets.simpleevents.domain.model.AlarmModel
import com.emikhalets.simpleevents.utils.AppAction

sealed class SettingsAction : AppAction {
    class ExportClick(val uri: Uri) : SettingsAction()
    class ImportClick(val uri: Uri) : SettingsAction()
    class SwitchAlarm(val entity: AlarmModel, val enabled: Boolean) : SettingsAction()
    class UpdateAlarm(val entity: AlarmModel) : SettingsAction()
    class DeleteAlarm(val entity: AlarmModel) : SettingsAction()
    object GetAlarms : SettingsAction()
    object ApplyError : SettingsAction()
    object ApplyImported : SettingsAction()
    object ApplyExported : SettingsAction()
}
