package com.emikhalets.simpleevents.data

import android.net.Uri
import com.emikhalets.simpleevents.core.common.extensions.execute
import com.emikhalets.simpleevents.core.common.extensions.logd
import com.emikhalets.simpleevents.core.database.DatabaseDataSource
import com.emikhalets.simpleevents.core.database.table_alarms.AlarmDb.Companion.toDb
import com.emikhalets.simpleevents.core.database.table_alarms.AlarmDb.Companion.toDbList
import com.emikhalets.simpleevents.core.database.table_alarms.AlarmDb.Companion.toModelFlow
import com.emikhalets.simpleevents.core.database.table_events.EventDb.Companion.toDb
import com.emikhalets.simpleevents.core.database.table_events.EventDb.Companion.toModel
import com.emikhalets.simpleevents.core.database.table_events.EventDb.Companion.toModelFlow
import com.emikhalets.simpleevents.core.file.FileDataSource
import com.emikhalets.simpleevents.domain.AppResult
import com.emikhalets.simpleevents.domain.model.AlarmModel
import com.emikhalets.simpleevents.domain.model.EventModel
import com.emikhalets.simpleevents.utils.extensions.daysLeft
import com.emikhalets.simpleevents.utils.extensions.turns
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class AppRepository @Inject constructor(
    private val databaseDataSource: DatabaseDataSource,
    private val fileDataSource: FileDataSource
) {

    suspend fun isAlarmExist(model: AlarmModel): AppResult<Boolean> {
        logd("isAlarmExist: $model")
        return execute {
            databaseDataSource.isAlarmExist(model.toDb())
        }
    }

    suspend fun insertAlarm(model: AlarmModel): AppResult<Long> {
        logd("insertAlarm: $model")
        return execute {
            databaseDataSource.insertAlarm(model.toDb())
        }
    }

    suspend fun insertAlarms(list: List<AlarmModel>): AppResult<List<Long>> {
        logd("insertAlarms: $list")
        return execute {
            databaseDataSource.insertAlarms(list.toDbList())
        }
    }

    suspend fun updateAlarm(model: AlarmModel): AppResult<Int> {
        logd("updateAlarm: $model")
        return execute {
            databaseDataSource.updateAlarm(model.toDb())
        }
    }

    suspend fun deleteAlarm(model: AlarmModel): AppResult<Int> {
        logd("deleteAlarm: $model")
        return execute {
            databaseDataSource.deleteAlarm(model.toDb())
        }
    }

    fun getAllAlarms(): Flow<List<AlarmModel>> {
        logd("getAllAlarms")
        return databaseDataSource.getAllAlarms().toModelFlow()
    }

    suspend fun insertEvent(model: EventModel): AppResult<Long> {
        logd("insertEvent: $model")
        return execute {
            databaseDataSource.insertEvent(model.toDb())
        }
    }

    suspend fun updateEvent(model: EventModel): AppResult<Int> {
        logd("updateEvent: $model")
        return execute {
            databaseDataSource.updateEvent(model.toDb())
        }
    }

    suspend fun deleteEvent(model: EventModel): AppResult<Int> {
        logd("deleteEvent: $model")
        return execute {
            databaseDataSource.deleteEvent(model.toDb())
        }
    }

    fun getAllEvents(): Flow<List<EventModel>> {
        logd("getAllEvents")
        return databaseDataSource.getAllEvents().toModelFlow()
    }

    suspend fun getEventById(id: Long): AppResult<EventModel> {
        logd("getEventById: $id")
        return execute {
            val dbModel = databaseDataSource.getEventById(id)
            val days = dbModel.date.daysLeft
            val age = dbModel.date.turns
            dbModel.toModel(days, age)
        }
    }

    suspend fun importEvents(uri: Uri): AppResult<List<EventModel>> {
        logd("importEvents: $uri")
        return execute { fileDataSource.importEvents(uri) }
    }

    suspend fun exportEvents(uri: Uri, events: List<EventModel>): AppResult<Boolean> {
        logd("exportEvents: $uri $events")
        return execute { fileDataSource.exportEvents(uri, events) }
    }
}
