package com.emikhalets.simpleevents.utils.extensions

import androidx.annotation.PluralsRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext

@Composable
@ReadOnlyComposable
fun pluralsResource(@PluralsRes res: Int, quantity: Int, vararg args: Any) =
    LocalContext.current.resources.getQuantityString(res, quantity, args)
