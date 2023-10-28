package com.emikhalets.simpleevents.domain.use_case.alarms

import com.emikhalets.simpleevents.domain.model.AlarmModel
import com.emikhalets.simpleevents.domain.repository.DatabaseRepository
import javax.inject.Inject

class DeleteAlarmUseCase @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) {

    suspend operator fun invoke(entity: AlarmModel) = databaseRepo.deleteEventAlarm(entity)
}
