package com.emikhalets.simpleevents.domain.usecase.alarms

import com.emikhalets.simpleevents.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetAlarmsUseCase @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) {

    suspend operator fun invoke() = databaseRepo.getAllEventsAlarm()
}
