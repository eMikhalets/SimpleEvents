package com.emikhalets.simpleevents.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.DeleteTable
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.emikhalets.simpleevents.data.database.dao.AlarmsDao
import com.emikhalets.simpleevents.data.database.dao.EventsDao
import com.emikhalets.simpleevents.domain.entity.AlarmEntity
import com.emikhalets.simpleevents.domain.entity.EventEntity

@Database(
    entities = [
        EventEntity::class,
        AlarmEntity::class,
    ],
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = Migration1To2::class),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4),
        AutoMigration(from = 4, to = 5, spec = Migration4To5::class)
    ],
    version = 5,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract val eventsDao: EventsDao
    abstract val alarmsDao: AlarmsDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room
                .databaseBuilder(context, AppDatabase::class.java, "SimpleEvents")
                .build()
        }
    }
}

@DeleteColumn(tableName = "events", columnName = "ageTurns")
@DeleteColumn(tableName = "events", columnName = "daysCount")
private class Migration1To2 : AutoMigrationSpec

@DeleteTable(tableName = "notifications_global")
private class Migration4To5 : AutoMigrationSpec
