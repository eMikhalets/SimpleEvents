package com.emikhalets.simpleevents.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.emikhalets.simpleevents.domain.entity.database.EventAlarm
import com.emikhalets.simpleevents.domain.entity.database.EventEntity

@Database(
    entities = [
        EventEntity::class,
        EventAlarm::class,
    ],
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = Migration1To2::class),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4),
        AutoMigration(from = 4, to = 5),
    ],
    version = 5,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract val eventsDao: EventsDao
    abstract val eventAlarmsDao: EventAlarmsDao

    companion object {
        fun get(context: Context): AppDatabase {
            return Room
                .databaseBuilder(context, AppDatabase::class.java, "SimpleEvents")
                .build()
        }
    }
}

@DeleteColumn(tableName = "events", columnName = "ageTurns")
@DeleteColumn(tableName = "events", columnName = "daysCount")
private class Migration1To2 : AutoMigrationSpec
