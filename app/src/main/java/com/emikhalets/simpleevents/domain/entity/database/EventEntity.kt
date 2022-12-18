package com.emikhalets.simpleevents.domain.entity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.emikhalets.simpleevents.data.database.Db.TableEvents.COL_DATE
import com.emikhalets.simpleevents.data.database.Db.TableEvents.COL_ID
import com.emikhalets.simpleevents.data.database.Db.TableEvents.COL_NAME
import com.emikhalets.simpleevents.data.database.Db.TableEvents.COL_NOTE
import com.emikhalets.simpleevents.data.database.Db.TableEvents.COL_TYPE
import com.emikhalets.simpleevents.data.database.Db.TableEvents.COL_WITHOUT_YEAR
import com.emikhalets.simpleevents.data.database.Db.TableEvents.NAME
import com.emikhalets.simpleevents.utils.enums.EventType
import kotlinx.serialization.Serializable

@Entity(tableName = NAME)
@Serializable
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COL_ID) val id: Long = 0,
    @ColumnInfo(name = COL_DATE) val date: Long,
    @ColumnInfo(name = COL_NAME) val name: String,
    @ColumnInfo(name = COL_TYPE) val eventType: EventType,
    @ColumnInfo(name = COL_NOTE) val note: String = "",
    @ColumnInfo(name = COL_WITHOUT_YEAR, defaultValue = "false") val withoutYear: Boolean = false,
    @Ignore val days: Int = 0,
    @Ignore val age: Int = 0,
) {

    constructor(
        id: Long,
        date: Long,
        name: String,
        eventType: EventType,
        note: String,
        withoutYear: Boolean,
    ) : this(id, date, name, eventType, note, withoutYear, 0, 0)

    @Ignore
    constructor(date: Long, name: String, eventType: EventType, withoutYear: Boolean)
            : this(0, date, name, eventType, "", withoutYear, 0, 0)
}