package com.emikhalets.simpleevents.utils.extensions

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
@ReadOnlyComposable
fun pluralsResource(@PluralsRes res: Int, quantity: Int, vararg args: Any) =
    LocalContext.current.resources.getQuantityString(res, quantity, args)

fun ScaffoldState.showSnackBar(message: String) {
    CoroutineScope(Dispatchers.IO).launch {
        snackbarHostState.showSnackbar(
            message = message,
            duration = SnackbarDuration.Short
        )
    }
}

fun ScaffoldState.showSnackBar(context: Context, @StringRes resource: Int) {
    CoroutineScope(Dispatchers.IO).launch {
        snackbarHostState.showSnackbar(
            message = context.getString(resource),
            duration = SnackbarDuration.Short
        )
    }
}