package com.emikhalets.simpleevents.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.presentation.theme.AppTheme
import com.emikhalets.simpleevents.utils.enums.EventType

@Composable
fun EventTypeSpinner(
    onTypeSelected: (EventType) -> Unit,
    modifier: Modifier = Modifier,
    initItem: EventType? = null,
) {
    val items = remember {
        val list = EventType.values()
        onTypeSelected(list.first())
        list
    }

    var expanded by remember { mutableStateOf(false) }
    var itemTextRes by remember { mutableStateOf(initItem?.nameRes ?: items.first().nameRes) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(50)
                )
                .clip(RoundedCornerShape(50))
                .clickable { expanded = !expanded }
                .padding(16.dp)
        ) {
            AppText(
                text = stringResource(itemTextRes),
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .fillMaxWidth()
                    .weight(1f)
            )
            AppIcon(
                drawableRes = R.drawable.ic_baseline_arrow_drop_down_24,
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier.rotate(if (!expanded) 0f else 180f)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach { type ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        itemTextRes = type.nameRes
                        onTypeSelected(type)
                    }
                ) {
                    Text(
                        text = stringResource(type.nameRes),
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        EventTypeSpinner(onTypeSelected = {}, modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp))
    }
}
