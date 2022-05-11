package com.emikhalets.simpleevents.utils.di

import android.content.Context
import com.emikhalets.simpleevents.data.database.AppDatabase
import com.emikhalets.simpleevents.data.database.EventsDao
import com.emikhalets.simpleevents.data.database.NotificationsGlobalDao
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
        return AppDatabase.get(context)
    }

    @Singleton
    @Provides
    fun providesEventsDao(database: AppDatabase): EventsDao {
        return database.eventsDao
    }

    @Singleton
    @Provides
    fun providesNotifGlobalDao(database: AppDatabase): NotificationsGlobalDao {
        return database.notificationsGlobalDao
    }
}