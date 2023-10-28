package com.emikhalets.simpleevents.core.database.table_alarms

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmsDao {

    @Insert
    suspend fun insert(entity: AlarmDb): Long

    @Insert
    suspend fun insert(list: List<AlarmDb>): List<Long>

    @Update
    suspend fun update(entity: AlarmDb): Int

    @Delete
    suspend fun delete(entity: AlarmDb): Int

    @Query("SELECT * FROM alarms WHERE id = :id")
    suspend fun getItem(id: Long): AlarmDb

    @Query("SELECT * FROM alarms ORDER BY days ASC")
    suspend fun getAll(): List<AlarmDb>

    @Query("SELECT * FROM alarms ORDER BY days ASC")
    fun getAllFlow(): Flow<List<AlarmDb>>

    @Query("SELECT EXISTS (SELECT * FROM alarms WHERE name = :name)")
    suspend fun isAlarmExist(name: String): Boolean

    @Query("SELECT EXISTS (SELECT * FROM alarms WHERE days = :days)")
    suspend fun isAlarmExist(days: Int): Boolean

    @Query("DELETE FROM alarms")
    suspend fun drop()
}
