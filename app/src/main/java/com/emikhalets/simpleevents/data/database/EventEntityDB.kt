package com.emikhalets.simpleevents.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emikhalets.simpleevents.utils.enums.EventType

@Entity(tableName = "events")
data class EventEntityDB(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "ageTurns") val ageTurns: Int,
    @ColumnInfo(name = "daysCount") val daysCount: Int,
    @ColumnInfo(name = "eventType") val eventType: EventType,
    @ColumnInfo(name = "note") val note: String,
)
