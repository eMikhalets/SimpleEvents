package com.emikhalets.simpleevents.domain.usecase.backups

import android.net.Uri
import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.domain.repository.AppRepository
import javax.inject.Inject

class ExportEventsUseCase @Inject constructor(
    private val appRepository: AppRepository,
) {

    suspend operator fun invoke(uri: Uri, events: List<EventEntity>) =
        appRepository.exportEvents(uri, events)
}
