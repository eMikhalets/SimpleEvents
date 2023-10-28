package com.emikhalets.simpleevents.domain.use_case.alarms

import com.emikhalets.simpleevents.data.AppRepository
import com.emikhalets.simpleevents.domain.AppResult
import com.emikhalets.simpleevents.domain.model.AlarmModel
import javax.inject.Inject

class DeleteAlarmUseCase @Inject constructor(
    private val repository: AppRepository,
) {

    suspend operator fun invoke(model: AlarmModel): AppResult<Int> {
        return repository.deleteAlarm(model)
    }
}
