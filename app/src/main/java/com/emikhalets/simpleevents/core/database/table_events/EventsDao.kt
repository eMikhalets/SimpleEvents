package com.emikhalets.simpleevents.core.database.table_events

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EventsDao {

    @Insert
    suspend fun insert(entity: EventDb): Long

    @Insert
    suspend fun insert(entities: List<EventDb>): List<Long>

    @Update
    suspend fun update(entity: EventDb): Int

    @Update
    suspend fun updateAll(entities: List<EventDb>): Int

    @Delete
    suspend fun delete(entity: EventDb): Int

    @Query("SELECT * FROM events WHERE id = :id")
    suspend fun getItem(id: Long): EventDb

    @Query("SELECT * FROM events")
    fun getAllFlow(): Flow<List<EventDb>>

    @Query("DELETE FROM events")
    suspend fun drop()
}
