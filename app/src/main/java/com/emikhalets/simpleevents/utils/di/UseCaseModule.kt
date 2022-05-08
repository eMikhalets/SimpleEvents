package com.emikhalets.simpleevents.utils.di

import com.emikhalets.simpleevents.data.repository.DatabaseRepository
import com.emikhalets.simpleevents.domain.usecase.HomeUseCase
import com.emikhalets.simpleevents.domain.usecase.HomeUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideHomeUseCase(databaseRepo: DatabaseRepository): HomeUseCase {
        return HomeUseCaseImpl(databaseRepo)
    }
}