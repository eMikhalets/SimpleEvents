package com.emikhalets.simpleevents.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emikhalets.simpleevents.domain.entity.database.EventAlarm

@Dao
interface EventAlarmsDao {

    @Insert
    suspend fun insert(entity: EventAlarm): Long

    @Insert
    suspend fun insert(list: List<EventAlarm>): List<Long>

    @Update
    suspend fun update(entity: EventAlarm): Int

    @Delete
    suspend fun delete(entity: EventAlarm): Int

    @Query("SELECT * FROM event_alarms ORDER BY days ASC")
    suspend fun getAll(): List<EventAlarm>

    @Query("DELETE FROM event_alarms")
    suspend fun drop()
}
