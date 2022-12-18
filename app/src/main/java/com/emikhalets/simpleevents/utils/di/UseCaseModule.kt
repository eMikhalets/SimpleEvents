package com.emikhalets.simpleevents.utils.di

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
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Singleton
    @Binds
    abstract fun bindsHomeUseCase(impl: HomeUseCaseImpl): HomeUseCase

    @Singleton
    @Binds
    abstract fun bindsAddEventUseCase(impl: AddEventUseCaseImpl): AddEventUseCase

    @Singleton
    @Binds
    abstract fun bindsEventItemUseCase(impl: EventItemUseCaseImpl): EventItemUseCase

    @Singleton
    @Binds
    abstract fun bindsEditEventUseCase(impl: EditEventUseCaseImpl): EditEventUseCase

    @Singleton
    @Binds
    abstract fun bindsSettingsUseCase(impl: SettingsUseCaseImpl): SettingsUseCase
}
