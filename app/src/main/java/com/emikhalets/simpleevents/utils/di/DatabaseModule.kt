package com.emikhalets.simpleevents.utils.di

import android.content.Context
import com.emikhalets.simpleevents.data.database.AppDatabase
import com.emikhalets.simpleevents.data.database.dao.AlarmsDao
import com.emikhalets.simpleevents.data.database.dao.EventsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
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
