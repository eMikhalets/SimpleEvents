package com.emikhalets.simpleevents.utils.di

import com.emikhalets.simpleevents.data.repository.DatabaseRepository
import com.emikhalets.simpleevents.domain.usecase.AddEventUseCase
import com.emikhalets.simpleevents.domain.usecase.AddEventUseCaseImpl
import com.emikhalets.simpleevents.domain.usecase.EditEventUseCase
import com.emikhalets.simpleevents.domain.usecase.EditEventUseCaseImpl
import com.emikhalets.simpleevents.domain.usecase.EventItemUseCase
import com.emikhalets.simpleevents.domain.usecase.EventItemUseCaseImpl
import com.emikhalets.simpleevents.domain.usecase.HomeUseCase
import com.emikhalets.simpleevents.domain.usecase.HomeUseCaseImpl
import com.emikhalets.simpleevents.domain.usecase.SettingsUseCase
import com.emikhalets.simpleevents.domain.usecase.SettingsUseCaseImpl
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

    @Singleton
    @Provides
    fun provideAddEventUseCase(databaseRepo: DatabaseRepository): AddEventUseCase {
        return AddEventUseCaseImpl(databaseRepo)
    }

    @Singleton
    @Provides
    fun provideEventItemUseCase(databaseRepo: DatabaseRepository): EventItemUseCase {
        return EventItemUseCaseImpl(databaseRepo)
    }

    @Singleton
    @Provides
    fun provideEditEventUseCase(databaseRepo: DatabaseRepository): EditEventUseCase {
        return EditEventUseCaseImpl(databaseRepo)
    }

    @Singleton
    @Provides
    fun provideSettingsUseCase(databaseRepo: DatabaseRepository): SettingsUseCase {
        return SettingsUseCaseImpl(databaseRepo)
    }
}