package com.emikhalets.simpleevents.utils.di

import com.emikhalets.simpleevents.data.database.EventsDao
import com.emikhalets.simpleevents.data.repository.DatabaseRepositoryImpl
import com.emikhalets.simpleevents.data.repository.DatabaseRepository
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
    fun bindDatabaseRepository(eventsDao: EventsDao): DatabaseRepository {
        return DatabaseRepositoryImpl(eventsDao)
    }
}