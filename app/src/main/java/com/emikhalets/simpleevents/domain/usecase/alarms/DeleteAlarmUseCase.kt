package com.emikhalets.simpleevents.domain.usecase.alarms

import com.emikhalets.simpleevents.domain.entity.AlarmEntity
import com.emikhalets.simpleevents.domain.repository.DatabaseRepository
import javax.inject.Inject

class DeleteAlarmUseCase @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) {

    suspend operator fun invoke(entity: AlarmEntity) = databaseRepo.deleteEventAlarm(entity)
}
