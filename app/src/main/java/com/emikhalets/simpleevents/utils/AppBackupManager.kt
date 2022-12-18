package com.emikhalets.simpleevents.utils

import android.content.Context
import android.net.Uri
import com.emikhalets.simpleevents.domain.entity.database.EventEntity
import com.emikhalets.simpleevents.domain.entity.EventEntityOld
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.FileReader
import javax.inject.Inject

class AppBackupManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    fun export(uri: Uri, events: List<EventEntity>): Boolean {
        val json = Json.encodeToString(events)
        return try {
            context.contentResolver.openFileDescriptor(uri, "w")?.use { descriptor ->
                FileOutputStream(descriptor.fileDescriptor).use { stream ->
                    stream.write(json.toByteArray())
                }
            }
            true
        } catch (e: Exception) {
            Timber.e(e)
            false
        }
    }

    fun import(uri: Uri): List<EventEntity> {
        return try {
            var json = ""
            context.contentResolver.openFileDescriptor(uri, "r")?.use { descriptor ->
                BufferedReader(FileReader(descriptor.fileDescriptor)).use { stream ->
                    json += stream.readLine()
                }
            }
            Json.decodeFromString(json)
        } catch (e: Exception) {
            Timber.e(e)
            emptyList()
        }
    }

    fun importOld(uri: Uri): List<EventEntityOld> {
        return try {
            var json = ""
            context.contentResolver.openFileDescriptor(uri, "r")?.use { descriptor ->
                BufferedReader(FileReader(descriptor.fileDescriptor)).use { stream ->
                    json += stream.readLine()
                }
            }
            Json.decodeFromString(json)
        } catch (e: Exception) {
            Timber.e(e)
            emptyList()
        }
    }
}