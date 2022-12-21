package com.emikhalets.simpleevents.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.emikhalets.simpleevents.presentation.theme.AppTheme
import com.emikhalets.simpleevents.presentation.theme.backgroundSecondary
import com.emikhalets.simpleevents.presentation.theme.onBackgroundSecondary

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    @DrawableRes leadingIcon: Int? = null,
    type: KeyboardType = KeyboardType.Text,
    maxLines: Int = Int.MAX_VALUE,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = if (placeholder != null) {
            { Text(placeholder) }
        } else null,
        leadingIcon = if (leadingIcon != null) {
            { AppIcon(leadingIcon) }
        } else null,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.primary,
            disabledTextColor = MaterialTheme.colors.onPrimary,
            backgroundColor = MaterialTheme.colors.backgroundSecondary,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = MaterialTheme.colors.error,
            leadingIconColor = MaterialTheme.colors.primary,
            disabledLeadingIconColor = MaterialTheme.colors.primary,
            errorLeadingIconColor = MaterialTheme.colors.error,
            placeholderColor = MaterialTheme.colors.onBackgroundSecondary,
            disabledPlaceholderColor = MaterialTheme.colors.onBackgroundSecondary
        ),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            keyboardType = type
        ),
        maxLines = maxLines,
        modifier = modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun TextButtonPreview() {
    AppTheme {
        AppTextField(value = "", onValueChange = {}, placeholder = "Some placeHolder")
    }
}
