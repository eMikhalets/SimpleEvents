package com.emikhalets.simpleevents.presentation.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.presentation.theme.AppTheme

@Composable
fun DeleteEventDialog(
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    AppDialog(
        title = null,
        message = stringResource(R.string.dialog_delete_event),
        leftButton = stringResource(R.string.dialog_cancel),
        rightButton = stringResource(R.string.dialog_delete),
        onLeftClick = onCancelClick,
        onRightClick = onDeleteClick
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewDeleteDialog() {
    AppTheme {
        DeleteEventDialog(
            onCancelClick = {},
            onDeleteClick = {}
        )
    }
}
