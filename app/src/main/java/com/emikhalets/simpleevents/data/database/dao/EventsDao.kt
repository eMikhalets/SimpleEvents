package com.emikhalets.simpleevents.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emikhalets.simpleevents.domain.entity.database.EventEntity

@Dao
interface EventsDao {

    @Insert
    suspend fun insert(entity: EventEntity): Long

    @Insert
    suspend fun insert(entities: List<EventEntity>): List<Long>

    @Update
    suspend fun update(entity: EventEntity): Int

    @Update
    suspend fun updateAll(entities: List<EventEntity>): Int

    @Delete
    suspend fun delete(entity: EventEntity): Int

    @Query("SELECT * FROM events")
    suspend fun getAllEntities(): List<EventEntity>

    @Query("SELECT * FROM events WHERE id = :id")
    suspend fun getEntityById(id: Long): EventEntity

    @Query("DELETE FROM events")
    suspend fun drop()
}
