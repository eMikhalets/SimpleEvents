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

    @Update
    suspend fun update(entity: EventEntityDB): Int

    @Delete
    suspend fun delete(entity: EventEntityDB): Int

    @Query("SELECT * FROM events")
    suspend fun getAllEntities(): List<EventEntityDB>
}