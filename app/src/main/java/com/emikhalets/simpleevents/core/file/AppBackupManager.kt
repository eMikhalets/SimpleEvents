package com.emikhalets.simpleevents.core.file

import android.content.Context
import android.net.Uri
import com.emikhalets.simpleevents.core.common.extensions.loge
import com.emikhalets.simpleevents.domain.model.EventModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.FileReader
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AppBackupManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    suspend fun export(uri: Uri, events: List<EventModel>): Boolean {
        return withContext(Dispatchers.IO) {
            val json = Json.encodeToString(events)
            try {
                context.contentResolver.openFileDescriptor(uri, "w")?.use { descriptor ->
                    FileOutputStream(descriptor.fileDescriptor).use { stream ->
                        stream.write(json.toByteArray())
                    }
                }
                true
            } catch (e: Exception) {
                loge(e)
                false
            }
        }
    }

    suspend fun import(uri: Uri): List<EventModel> {
        return withContext(Dispatchers.IO) {
            try {
                var json = ""
                context.contentResolver.openFileDescriptor(uri, "r")?.use { descriptor ->
                    BufferedReader(FileReader(descriptor.fileDescriptor)).use { stream ->
                        json += stream.readLine()
                    }
                }
                Json.decodeFromString(json)
            } catch (e: Exception) {
                loge(e)
                emptyList()
            }
        }
    }
}
