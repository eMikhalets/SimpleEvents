package com.emikhalets.simpleevents.utils.di

import com.emikhalets.simpleevents.data.repository.AppRepository
import com.emikhalets.simpleevents.data.repository.AppRepositoryImpl
import com.emikhalets.simpleevents.data.repository.DatabaseRepository
import com.emikhalets.simpleevents.data.repository.DatabaseRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsAppRepository(impl: AppRepositoryImpl): AppRepository

    @Singleton
    @Binds
    abstract fun bindsDatabaseRepository(impl: DatabaseRepositoryImpl): DatabaseRepository
}
