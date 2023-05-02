package com.emikhalets.simpleevents.presentation.screens.group_edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.presentation.components.AppButton
import com.emikhalets.simpleevents.presentation.components.AppTextField
import com.emikhalets.simpleevents.presentation.components.dialogs.ErrorDialog
import com.emikhalets.simpleevents.presentation.theme.AppTheme

@Composable
fun GroupEditScreen(
    groupId: Long?,
    state: GroupEditState,
    onAction: (GroupEditAction) -> Unit,
    onGroupSaved: () -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var alarm by remember { mutableStateOf(false) }
    var needSave by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (groupId != null) {
            onAction(GroupEditAction.GetGroup(groupId))
        }
    }

    LaunchedEffect(state.group) {
        if (state.group != null) {
            name = state.group.name
            alarm = state.group.isAlarmsEnabled
        }
    }

    LaunchedEffect(state.saved) {
        if (state.saved) {
            onGroupSaved()
        }
    }

    LaunchedEffect(state.error) {
        val error = state.error
        if (error != null) {
            errorMessage = error.asString()
            onAction(GroupEditAction.AcceptError)
        }
    }

    ScreenContent(
        name = name,
        alarm = alarm,
        needSave = needSave,
        onAlarmSwitch = {
            alarm = it
            needSave = true
        },
        onNameChange = {
            name = it
            needSave = true
        },
        onSaveClick = {
            onAction(GroupEditAction.UpdateGroup(name, alarm))
        }
    )

    if (errorMessage.isNotEmpty()) {
        ErrorDialog(
            message = errorMessage,
            onOkClick = { errorMessage = "" }
        )
    }
}

@Composable
private fun ScreenContent(
    name: String,
    alarm: Boolean,
    needSave: Boolean,
    onAlarmSwitch: (Boolean) -> Unit,
    onNameChange: (String) -> Unit,
    onSaveClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        GroupInfoBox(
            name = name,
            alarm = alarm,
            onAlarmSwitch = onAlarmSwitch,
            onNameChange = onNameChange
        )
        if (needSave) {
            AppButton(
                text = stringResource(id = R.string.group_edit_save),
                onClick = onSaveClick
            )
        }
    }
}

@Composable
private fun GroupInfoBox(
    name: String,
    alarm: Boolean,
    onAlarmSwitch: (Boolean) -> Unit,
    onNameChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        AppTextField(
            value = name,
            onValueChange = onNameChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .padding(horizontal = 16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.group_item_alarms),
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Switch(
                checked = alarm,
                onCheckedChange = { onAlarmSwitch(it) },
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            name = "Group name",
            alarm = true,
            needSave = true,
            onAlarmSwitch = {},
            onNameChange = {},
            onSaveClick = {}
        )
    }
}
