package com.emikhalets.simpleevents.domain.use_case.alarms

import com.emikhalets.simpleevents.data.AppRepository
import com.emikhalets.simpleevents.domain.AppResult
import com.emikhalets.simpleevents.domain.model.AlarmModel
import javax.inject.Inject

class AddAlarmUseCase @Inject constructor(
    private val repository: AppRepository,
) {

    suspend operator fun invoke(model: AlarmModel): AppResult<Long> {
        return repository.insertAlarm(model)
    }

    suspend operator fun invoke(list: List<AlarmModel>): AppResult<List<Long>> {
        return repository.insertAlarms(list)
    }
}
