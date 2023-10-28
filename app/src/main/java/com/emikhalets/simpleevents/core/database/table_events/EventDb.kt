package com.emikhalets.simpleevents.core.database.table_events

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emikhalets.simpleevents.domain.model.EventModel
import com.emikhalets.simpleevents.utils.enums.EventType
import com.emikhalets.simpleevents.utils.extensions.daysLeft
import com.emikhalets.simpleevents.utils.extensions.turns
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

@Entity(tableName = "events")
@Serializable
data class EventDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "date")
    val date: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "eventType")
    val eventType: EventType,
    @ColumnInfo(name = "note")
    val note: String,
    @ColumnInfo(name = "without_year")
    val withoutYear: Boolean,
) {

    companion object {

        fun EventModel.toDb(): EventDb {
            return EventDb(id, date, name, eventType, note, withoutYear)
        }

        fun List<EventModel>.toDbList(): List<EventDb> {
            return map { it.toDb() }
        }

        fun Flow<List<EventModel>>.toDbFlow(): Flow<List<EventDb>> {
            return map { it.toDbList() }
        }

        fun EventDb.toModel(days: Int, age: Int): EventModel {
            return EventModel(id, date, name, eventType, note, withoutYear, days, age)
        }

        fun List<EventDb>.toModelList(): List<EventModel> {
            return map {
                val days = it.date.daysLeft
                val age = it.date.turns
                it.toModel(days, age)
            }
        }

        fun Flow<List<EventDb>>.toModelFlow(): Flow<List<EventModel>> {
            return map { it.toModelList() }
        }
    }
}
