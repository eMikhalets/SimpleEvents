package com.emikhalets.simpleevents.presentation.screens.group_item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.domain.entity.GroupEntity
import com.emikhalets.simpleevents.domain.entity.PreviewEntity
import com.emikhalets.simpleevents.presentation.components.dialogs.ErrorDialog
import com.emikhalets.simpleevents.presentation.theme.AppTheme

@Composable
fun GroupItemScreen(
    groupId: Long?,
    state: GroupItemState,
    onAction: (GroupItemAction) -> Unit,
) {
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        onAction(GroupItemAction.GetGroup(groupId))
    }

    LaunchedEffect(state.group) {
        if (state.group != null) {
            onAction(GroupItemAction.GetEvents)
        }
    }

    LaunchedEffect(state.error) {
        val error = state.error
        if (error != null) {
            errorMessage = error.asString()
            onAction(GroupItemAction.AcceptError)
        }
    }

    ScreenContent(
        state = state,
        onEditClick = {},
        onDeleteClick = {},
        onAlarmSwitch = {},
        onEventClick = {}
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
    state: GroupItemState,
    onEditClick: (Long) -> Unit,
    onDeleteClick: () -> Unit,
    onAlarmSwitch: (Boolean) -> Unit,
    onEventClick: (Long) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (state.group != null) {
            GroupInfoBox(
                entity = state.group,
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick,
                onAlarmSwitch = onAlarmSwitch
            )
        }
        EventsListBox(
            list = state.eventsList,
            onEventClick = onEventClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun GroupInfoBox(
    entity: GroupEntity,
    onEditClick: (Long) -> Unit,
    onDeleteClick: () -> Unit,
    onAlarmSwitch: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = entity.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                modifier = Modifier
                    .clickable { onEditClick(entity.id) }
                    .padding(start = 16.dp)
            )
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                modifier = Modifier
                    .clickable { onDeleteClick() }
                    .padding(start = 32.dp)
            )
        }
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
                checked = entity.isAlarmsEnabled,
                onCheckedChange = { onAlarmSwitch(it) },
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
private fun EventsListBox(
    list: List<EventEntity>,
    onEventClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(list) { event ->
            EventItemBox(
                event = event,
                onEventClick = onEventClick
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@Composable
private fun EventItemBox(
    event: EventEntity,
    onEventClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onEventClick(event.id) }
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = event.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            state = GroupItemState(
                group = GroupEntity(0, "GroupName", true),
                eventsList = PreviewEntity.getEventsList(5),
            ),
            onEditClick = {},
            onDeleteClick = {},
            onAlarmSwitch = {},
            onEventClick = {}
        )
    }
}
