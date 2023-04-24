package com.emikhalets.simpleevents.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emikhalets.simpleevents.domain.entity.database.EventAlarm
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmsDao {

    @Insert
    suspend fun insert(entity: EventAlarm): Long

    @Insert
    suspend fun insert(list: List<EventAlarm>): List<Long>

    @Update
    suspend fun update(entity: EventAlarm): Int

    @Delete
    suspend fun delete(entity: EventAlarm): Int

    @Query("SELECT * FROM alarms WHERE id = :id")
    suspend fun getItem(id: Long): EventAlarm

    @Query("SELECT * FROM alarms ORDER BY days ASC")
    suspend fun getAll(): List<EventAlarm>

    @Query("SELECT * FROM alarms ORDER BY days ASC")
    fun getAllFlow(): Flow<List<EventAlarm>>

    @Query("SELECT EXISTS (SELECT * FROM alarms WHERE name_en = :name)")
    fun isNotificationNameExist(name: String): Boolean

    @Query("SELECT EXISTS (SELECT * FROM alarms WHERE days = :days)")
    fun isNotificationDaysExist(days: Int): Boolean

    @Query("DELETE FROM alarms")
    suspend fun drop()
}
