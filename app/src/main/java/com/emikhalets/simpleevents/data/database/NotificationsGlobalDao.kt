package com.emikhalets.simpleevents.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotificationsGlobalDao {

    @Insert
    suspend fun insert(entity: NotificationGlobal): Long

    @Insert
    suspend fun insert(list: List<NotificationGlobal>): List<Long>

    @Update
    suspend fun update(entity: NotificationGlobal): Int

    @Delete
    suspend fun delete(entity: NotificationGlobal): Int

    @Query("SELECT * FROM notifications_global")
    suspend fun getAllEntities(): List<NotificationGlobal>

    @Query("DELETE FROM notifications_global")
    suspend fun drop()
}