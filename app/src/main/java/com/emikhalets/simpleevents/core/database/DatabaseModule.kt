package com.emikhalets.simpleevents.core.database

import android.content.Context
import androidx.room.Room
import com.emikhalets.simpleevents.core.database.table_alarms.AlarmsDao
import com.emikhalets.simpleevents.core.database.table_events.EventsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        val name = "EventsApp.db"
        return Room
            .databaseBuilder(context, AppDatabase::class.java, name)
            .build()
    }

    @Singleton
    @Provides
    fun providesEventsDao(database: AppDatabase): EventsDao {
        return database.eventsDao
    }

    @Singleton
    @Provides
    fun providesEventAlarmsDao(database: AppDatabase): AlarmsDao {
        return database.alarmsDao
    }
}
