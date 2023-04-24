package com.emikhalets.simpleevents.domain.usecase.alarms

import com.emikhalets.simpleevents.domain.entity.AlarmEntity
import com.emikhalets.simpleevents.domain.repository.DatabaseRepository
import javax.inject.Inject

class AddAlarmUseCase @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) {

    suspend operator fun invoke(entity: AlarmEntity) = when (entity.id) {
        0L -> databaseRepo.insertEventAlarm(entity)
        else -> databaseRepo.updateEventAlarm(entity)
    }
}
