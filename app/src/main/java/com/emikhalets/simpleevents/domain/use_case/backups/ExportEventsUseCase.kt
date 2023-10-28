package com.emikhalets.simpleevents.domain.use_case.backups

import android.net.Uri
import com.emikhalets.simpleevents.data.AppRepository
import com.emikhalets.simpleevents.domain.AppResult
import com.emikhalets.simpleevents.domain.model.EventModel
import javax.inject.Inject

class ExportEventsUseCase @Inject constructor(
    private val repository: AppRepository,
) {

    suspend operator fun invoke(uri: Uri, events: List<EventModel>): AppResult<Boolean> {
        return repository.exportEvents(uri, events)
    }
}
