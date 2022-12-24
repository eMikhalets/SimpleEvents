package com.emikhalets.simpleevents.domain.entity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "event_alarms")
data class EventAlarm(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "name_en") val nameEn: String,
    @ColumnInfo(name = "enabled") val enabled: Boolean,
    @ColumnInfo(name = "days") val days: Int,
) {

    @Ignore
    constructor(nameEn: String, enabled: Boolean, days: Int) : this(0, nameEn, enabled, days)

    @Ignore
    constructor(nameEn: String, days: Int) : this(0, nameEn, false, days)
}
