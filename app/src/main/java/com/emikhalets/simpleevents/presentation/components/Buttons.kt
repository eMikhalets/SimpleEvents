package com.emikhalets.simpleevents.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.simpleevents.presentation.theme.AppColors
import com.emikhalets.simpleevents.presentation.theme.AppTheme

@Composable
fun AppIconButton(
    @DrawableRes drawableRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primary,
    iconColor: Color = MaterialTheme.colors.onPrimary,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        AppIcon(
            drawableRes = drawableRes,
            tint = iconColor,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = MaterialTheme.colors.onPrimary,
) {
    Button(
        shape = RectangleShape,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        ),
        modifier = modifier
    ) {
        AppText(
            text = text,
            color = MaterialTheme.colors.onPrimary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
fun AppTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = AppColors.Blue_800,
) {
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = textColor
        ),
        modifier = modifier
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ButtonPreview() {
    AppTheme {
        AppButton(text = "Some text", onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun TextButtonPreview() {
    AppTheme {
        AppTextButton(text = "Some text", onClick = {})
    }
}
