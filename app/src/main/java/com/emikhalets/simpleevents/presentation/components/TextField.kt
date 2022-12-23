package com.emikhalets.simpleevents.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import com.emikhalets.simpleevents.presentation.theme.AppTheme

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    @DrawableRes leadingIcon: Int? = null,
    type: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
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
            textColor = MaterialTheme.colors.onBackground,
            disabledTextColor = MaterialTheme.colors.onBackground,
            backgroundColor = MaterialTheme.colors.background,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = MaterialTheme.colors.error,
            leadingIconColor = MaterialTheme.colors.onBackground,
            disabledLeadingIconColor = MaterialTheme.colors.onBackground,
            errorLeadingIconColor = MaterialTheme.colors.error,
            placeholderColor = MaterialTheme.colors.onSecondary,
            disabledPlaceholderColor = MaterialTheme.colors.onSecondary
        ),
        keyboardOptions = KeyboardOptions(
            capitalization = capitalization,
            keyboardType = type
        ),
        shape = RoundedCornerShape(26.dp),
        maxLines = maxLines,
        modifier = modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        AppTextField(value = "",
            onValueChange = {},
            placeholder = "Some placeHolder",
            modifier = Modifier.padding(32.dp))
    }
}
