package com.emikhalets.simpleevents.core.database.table_alarms

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emikhalets.simpleevents.domain.model.AlarmModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

@Entity(tableName = "alarms")
@Serializable
data class AlarmDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "enabled")
    val enabled: Boolean,
    @ColumnInfo(name = "days")
    val days: Int,
) {

    companion object {

        fun AlarmModel.toDb(): AlarmDb {
            return AlarmDb(id, name, enabled, days)
        }

        fun List<AlarmModel>.toDbList(): List<AlarmDb> {
            return map { it.toDb() }
        }

        fun Flow<List<AlarmModel>>.toDbFlow(): Flow<List<AlarmDb>> {
            return map { it.toDbList() }
        }

        fun AlarmDb.toModel(): AlarmModel {
            return AlarmModel(id, name, enabled, days)
        }

        fun List<AlarmDb>.toModelList(): List<AlarmModel> {
            return map { it.toModel() }
        }

        fun Flow<List<AlarmDb>>.toModelFlow(): Flow<List<AlarmModel>> {
            return map { it.toModelList() }
        }
    }
}
