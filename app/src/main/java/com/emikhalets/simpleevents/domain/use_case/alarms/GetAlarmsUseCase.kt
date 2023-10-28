package com.emikhalets.simpleevents.domain.use_case.alarms

import com.emikhalets.simpleevents.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetAlarmsUseCase @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) {

    suspend operator fun invoke() = databaseRepo.getAllEventsAlarm()
}
