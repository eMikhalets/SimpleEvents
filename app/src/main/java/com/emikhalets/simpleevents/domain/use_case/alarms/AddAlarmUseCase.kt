package com.emikhalets.simpleevents.domain.use_case.alarms

import com.emikhalets.simpleevents.domain.model.AlarmModel
import com.emikhalets.simpleevents.domain.repository.DatabaseRepository
import javax.inject.Inject

class AddAlarmUseCase @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) {

    suspend operator fun invoke(entity: AlarmModel) = when (entity.id) {
        0L -> databaseRepo.insertEventAlarm(entity)
        else -> databaseRepo.updateEventAlarm(entity)
    }
}
