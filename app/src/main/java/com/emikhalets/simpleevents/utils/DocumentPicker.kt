package com.emikhalets.simpleevents.utils

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun documentPicker(callback: (Uri) -> Unit): ManagedActivityResultLauncher<Array<String>, Uri?> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri -> uri?.let(callback) }
    )
}

fun ManagedActivityResultLauncher<Array<String>, Uri?>.openFile() {
    launch(arrayOf("application/json", "text/plain"))
}