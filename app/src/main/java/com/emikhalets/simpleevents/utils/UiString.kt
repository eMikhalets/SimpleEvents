package com.emikhalets.simpleevents.utils

import android.content.res.Resources
import androidx.annotation.StringRes
import com.emikhalets.simpleevents.R

sealed class UiString {

    data class Message(
        val value: String?,
    ) : UiString()

    class Resource(
        @StringRes val resId: Int,
        vararg val args: Any,
    ) : UiString()

    fun asString(): String = when (this) {
        is Message -> value ?: Resources.getSystem().getString(R.string.error_internal)
        is Resource -> Resources.getSystem().getString(resId, *args)
    }

    companion object {
        val internal: UiString
            get() = Resource(R.string.error_internal)
    }
}
