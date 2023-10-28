package com.emikhalets.simpleevents.domain.use_case.alarms

import com.emikhalets.simpleevents.data.AppRepository
import com.emikhalets.simpleevents.domain.model.AlarmModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAlarmsUseCase @Inject constructor(
    private val repository: AppRepository,
) {

    operator fun invoke(): Flow<List<AlarmModel>> {
        return repository.getAllAlarms()
    }
}
