package com.emikhalets.simpleevents.presentation.screens.groups

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.simpleevents.domain.entity.GroupEntity
import com.emikhalets.simpleevents.domain.entity.PreviewEntity
import com.emikhalets.simpleevents.presentation.components.dialogs.ErrorDialog
import com.emikhalets.simpleevents.presentation.theme.AppTheme

@Composable
fun GroupsScreen(
    state: GroupsState,
    onAction: (GroupsAction) -> Unit,
    onGroupClick: (Long) -> Unit,
    onAddGroupClick: () -> Unit,
) {
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        onAction(GroupsAction.GetGroups)
    }

    LaunchedEffect(state.error) {
        val error = state.error
        if (error != null) {
            errorMessage = error.asString()
            onAction(GroupsAction.AcceptError)
        }
    }

    ScreenContent(
        state = state,
        onGroupClick = onGroupClick,
        onGroupSwitch = { entity, enabled -> onAction(GroupsAction.UpdateGroup(entity, enabled)) },
        onAddGroupClick = onAddGroupClick
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
    state: GroupsState,
    onGroupClick: (Long) -> Unit,
    onGroupSwitch: (GroupEntity, Boolean) -> Unit,
    onAddGroupClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        GroupsListBox(
            list = state.groupsList,
            onGroupClick = onGroupClick,
            onGroupSwitch = onGroupSwitch,
        )
        FloatingActionButton(
            onClick = onAddGroupClick,
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
}

@Composable
private fun GroupsListBox(
    list: List<GroupEntity>,
    onGroupClick: (Long) -> Unit,
    onGroupSwitch: (GroupEntity, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(list) { group ->
            GroupItemBox(
                group = group,
                onGroupClick = onGroupClick,
                onGroupSwitch = onGroupSwitch,
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@Composable
private fun GroupItemBox(
    group: GroupEntity,
    onGroupClick: (Long) -> Unit,
    onGroupSwitch: (GroupEntity, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onGroupClick(group.id) }
    ) {
        Text(
            text = group.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Icon(
            imageVector = Icons.Default.NotificationsActive,
            contentDescription = null
        )
        Switch(
            checked = group.isAlarmsEnabled,
            onCheckedChange = { onGroupSwitch(group, it) },
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            state = GroupsState(
                groupsList = PreviewEntity.getGroupsList()
            ),
            onGroupClick = {},
            onGroupSwitch = { _, _ -> },
            onAddGroupClick = {}
        )
    }
}
