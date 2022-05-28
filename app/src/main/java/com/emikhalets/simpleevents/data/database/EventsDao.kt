package com.emikhalets.simpleevents.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emikhalets.simpleevents.data.database.Db.TableEvents.COL_ID
import com.emikhalets.simpleevents.data.database.Db.TableEvents.NAME
import com.emikhalets.simpleevents.domain.entity.EventEntity

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

    @Query("SELECT * FROM $NAME")
    suspend fun getAllEntities(): List<EventEntity>

    @Query("SELECT * FROM $NAME WHERE $COL_ID = :id")
    suspend fun getEntityById(id: Long): EventEntity

    @Query("DELETE FROM $NAME")
    suspend fun drop()
}