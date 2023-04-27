package com.emikhalets.simpleevents.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emikhalets.simpleevents.data.database.entity.GroupDb
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupsDao {

    @Insert
    suspend fun insert(entity: GroupDb): Long

    @Update
    suspend fun update(entity: GroupDb): Int

    @Delete
    suspend fun delete(entity: GroupDb): Int

    @Query("SELECT * FROM groups")
    fun getAllEntities(): Flow<List<GroupDb>>

    @Query("SELECT * FROM groups WHERE id = :id ORDER BY name ASC")
    fun getEntityByIdFlow(id: Long): Flow<GroupDb>

    @Query("SELECT * FROM groups WHERE id = :id")
    suspend fun getEntityById(id: Long): GroupDb

    @Query("DELETE FROM groups")
    suspend fun drop()
}
