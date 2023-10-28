package com.emikhalets.simpleevents.domain.use_case.backups

import android.net.Uri
import com.emikhalets.simpleevents.data.AppRepository
import com.emikhalets.simpleevents.domain.AppResult
import com.emikhalets.simpleevents.domain.model.EventModel
import javax.inject.Inject

class ImportEventsUseCase @Inject constructor(
    private val repository: AppRepository,
) {

    suspend operator fun invoke(uri: Uri): AppResult<List<EventModel>> {
        return repository.importEvents(uri)
    }
}
