package com.emikhalets.simpleevents.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EventsDao {

    @Insert
    suspend fun insert(entity: EventEntityDB): Long

    @Insert
    suspend fun insert(entities: List<EventEntityDB>): List<Long>

    @Update
    suspend fun update(entity: EventEntityDB): Int

    @Update
    suspend fun updateAll(entities: List<EventEntityDB>): Int

    @Delete
    suspend fun delete(entity: EventEntityDB): Int

    @Query("SELECT * FROM events ORDER BY daysCount ASC")
    suspend fun getAllEntities(): List<EventEntityDB>

    @Query("SELECT * FROM events WHERE id=:id")
    suspend fun getEntityById(id: Long): EventEntityDB

    @Query("SELECT * FROM events WHERE daysCount=:days")
    suspend fun getEntityByDaysLeft(days: Int): List<EventEntityDB>

    @Query("DELETE FROM events")
    suspend fun drop()
}