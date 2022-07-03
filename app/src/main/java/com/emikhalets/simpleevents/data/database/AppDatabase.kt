package com.emikhalets.simpleevents.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.domain.entity.NotificationGlobal

@Database(
    entities = [
        EventEntity::class,
        NotificationGlobal::class,
    ],
    version = 4,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = Migration1To2::class),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4),
    ],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract val eventsDao: EventsDao
    abstract val notificationsGlobalDao: NotificationsGlobalDao

    companion object {
        fun get(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                Db.NAME
            ).build()
        }
    }
}

object Db {
    const val NAME = "SimpleEvents"

    object TableEvents {
        const val NAME = "events"
        const val COL_ID = "id"
        const val COL_DATE = "date"
        const val COL_NAME = "name"
        const val COL_AGE = "ageTurns"
        const val COL_DAYS = "daysCount"
        const val COL_TYPE = "eventType"
        const val COL_NOTE = "note"
        const val COL_WITHOUT_YEAR = "without_year"
    }

    object TableNotifications {
        const val NAME = "notifications_global"
        const val COL_ID = "id"
        const val COL_NAME = "name_res"
        const val COL_NAME_EN = "name_en"
        const val COL_ENABLED = "enabled"
        const val COL_DAYS = "days_left"
    }
}

@DeleteColumn(tableName = Db.TableEvents.NAME, columnName = Db.TableEvents.COL_AGE)
@DeleteColumn(tableName = Db.TableEvents.NAME, columnName = Db.TableEvents.COL_DAYS)
class Migration1To2 : AutoMigrationSpec