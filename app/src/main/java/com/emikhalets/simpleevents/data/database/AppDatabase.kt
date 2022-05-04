package com.emikhalets.simpleevents.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [EventEntityDB::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract val eventsDao: EventsDao
}