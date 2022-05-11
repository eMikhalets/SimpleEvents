package com.emikhalets.simpleevents.utils.di

import com.emikhalets.simpleevents.data.database.EventsDao
import com.emikhalets.simpleevents.data.database.NotificationsGlobalDao
import com.emikhalets.simpleevents.data.repository.DatabaseRepository
import com.emikhalets.simpleevents.data.repository.DatabaseRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun bindDatabaseRepository(
        eventsDao: EventsDao,
        notifGlobalDao: NotificationsGlobalDao,
    ): DatabaseRepository {
        return DatabaseRepositoryImpl(
            eventsDao = eventsDao,
            notifGlobalDao = notifGlobalDao
        )
    }
}