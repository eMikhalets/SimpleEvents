package com.emikhalets.simpleevents.presentation.screens.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.presentation.theme.SimpleEventsTheme
import com.emikhalets.simpleevents.presentation.theme.backgroundNegative

@Composable
fun DeletingEventDialog(
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissClick,
        confirmButton = {
            Button(
                onClick = onConfirmClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background
                )
            ) {
                Text(text = stringResource(R.string.dialog_delete))
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.backgroundNegative
                )
            ) {
                Text(
                    text = stringResource(R.string.dialog_cancel),
                    color = MaterialTheme.colors.onBackground
                )
            }
        },
        text = {
            Text(
                text = stringResource(R.string.dialog_delete_event),
                fontSize = 20.sp,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewDeleteDialog() {
    SimpleEventsTheme {
        DeletingEventDialog(
            onConfirmClick = {},
            onDismissClick = {}
        )
    }
}