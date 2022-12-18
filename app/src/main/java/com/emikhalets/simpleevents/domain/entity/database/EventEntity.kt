package com.emikhalets.simpleevents.domain.entity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.emikhalets.simpleevents.utils.enums.EventType
import kotlinx.serialization.Serializable

@Entity(tableName = "events")
@Serializable
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "eventType") val eventType: EventType,
    @ColumnInfo(name = "note") val note: String = "",
    @ColumnInfo(name = "without_year", defaultValue = "false") val withoutYear: Boolean = false,
) {

    @Ignore
    var days: Int = 0

    @Ignore
    var age: Int = 0

    @Ignore
    constructor(date: Long, name: String, eventType: EventType, withoutYear: Boolean)
            : this(0, date, name, eventType, "", withoutYear)
}
