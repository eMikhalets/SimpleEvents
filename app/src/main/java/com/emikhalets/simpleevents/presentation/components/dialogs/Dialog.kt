package com.emikhalets.simpleevents.presentation.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emikhalets.simpleevents.presentation.components.AppText
import com.emikhalets.simpleevents.presentation.components.AppTextButton
import com.emikhalets.simpleevents.presentation.theme.AppTheme

@Composable
fun AppDialog(
    title: String?,
    message: String,
    leftButton: String?,
    rightButton: String,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        DialogLayout(
            title = title,
            message = message,
            leftButton = leftButton,
            rightButton = rightButton,
            onLeftClick = onLeftClick,
            onRightClick = onRightClick
        )
    }
}

@Composable
private fun DialogLayout(
    title: String?,
    message: String,
    leftButton: String?,
    rightButton: String,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(40.dp)
            .background(
                color = MaterialTheme.colors.background,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        if (title != null) {
            AppText(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
        AppText(
            text = message,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (leftButton != null) {
                AppTextButton(
                    text = leftButton,
                    onClick = { onLeftClick() },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            AppTextButton(
                text = rightButton,
                onClick = { onRightClick() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        DialogLayout(
            title = "Dialog title",
            message = "Dialog message",
            leftButton = "Left btn",
            rightButton = "Right btn",
            onLeftClick = {},
            onRightClick = {}
        )
    }
}
