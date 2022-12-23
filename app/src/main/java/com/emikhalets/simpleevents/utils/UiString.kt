package com.emikhalets.simpleevents.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.emikhalets.simpleevents.R

sealed class UiString {

    data class Message(
        val value: String?,
    ) : UiString()

    class Resource(
        @StringRes val resId: Int,
        vararg val args: Any,
    ) : UiString()

    @Composable
    fun asString(): String = when (this) {
        is Message -> value ?: stringResource(R.string.error_internal)
        is Resource -> stringResource(resId, *args)
    }

    fun asString(context: Context): String = when (this) {
        is Message -> value ?: context.getString(R.string.error_internal)
        is Resource -> context.getString(resId, *args)
    }

    companion object {
        val internal: UiString
            get() = Resource(R.string.error_internal)
    }
}
