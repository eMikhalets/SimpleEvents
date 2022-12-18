package com.emikhalets.simpleevents.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emikhalets.simpleevents.data.database.Db.TableNotifications.COL_DAYS
import com.emikhalets.simpleevents.data.database.Db.TableNotifications.NAME
import com.emikhalets.simpleevents.domain.entity.database.NotificationGlobal

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

    @Query("SELECT * FROM $NAME ORDER BY $COL_DAYS ASC")
    suspend fun getAllEntities(): List<NotificationGlobal>

    @Query("DELETE FROM $NAME")
    suspend fun drop()
}