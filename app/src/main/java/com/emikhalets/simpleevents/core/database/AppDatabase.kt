package com.emikhalets.simpleevents.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.emikhalets.simpleevents.core.database.table_alarms.AlarmDb
import com.emikhalets.simpleevents.core.database.table_alarms.AlarmsDao
import com.emikhalets.simpleevents.core.database.table_events.EventDb
import com.emikhalets.simpleevents.core.database.table_events.EventsDao

@Database(
    entities = [
        AlarmDb::class,
        EventDb::class,
    ],
    autoMigrations = [],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract val eventsDao: EventsDao
    abstract val alarmsDao: AlarmsDao
}
