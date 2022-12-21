package com.emikhalets.simpleevents.presentation.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.presentation.theme.AppTheme

@Composable
fun ErrorDialog(
    message: String,
    onOkClick: () -> Unit,
) {
    AppDialog(
        title = stringResource(R.string.dialog_error),
        message = message,
        leftButton = null,
        rightButton = stringResource(R.string.dialog_ok),
        onLeftClick = {},
        onRightClick = onOkClick,
        onDismiss = onOkClick
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ErrorDialog(
            message = "Error message",
            onOkClick = {}
        )
    }
}
