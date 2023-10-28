package com.emikhalets.simpleevents.domain.use_case.backups

import android.net.Uri
import com.emikhalets.simpleevents.domain.model.EventModel
import com.emikhalets.simpleevents.domain.repository.AppRepository
import javax.inject.Inject

class ExportEventsUseCase @Inject constructor(
    private val appRepository: AppRepository,
) {

    suspend operator fun invoke(uri: Uri, events: List<EventModel>) =
        appRepository.exportEvents(uri, events)
}
