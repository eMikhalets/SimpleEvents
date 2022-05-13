package com.emikhalets.simpleevents.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun documentCreator(callback: (Uri) -> Unit): ManagedActivityResultLauncher<String, Uri?> {
    return rememberLauncherForActivityResult(
        contract = CreateFileContract(),
        onResult = { uri -> uri?.let(callback) }
    )
}

fun ManagedActivityResultLauncher<String, Uri?>.createFile() {
    launch("")
}

class CreateFileContract : ActivityResultContract<String, Uri?>() {

    override fun createIntent(context: Context, input: String): Intent {
        return Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            type = "application/json"
            putExtra(Intent.EXTRA_TITLE, getFileName())
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        if (intent == null || resultCode != Activity.RESULT_OK) return null
        return intent.data
    }

    private fun getFileName(): String {
        val formatter = SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.getDefault())
        val name = "SimpleEvents_Backup_${formatter.format(Date())}"
        val suffix = ".json"
        return "$name$suffix"
    }
}