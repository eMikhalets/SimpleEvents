package com.emikhalets.simpleevents.domain.use_case.backups

import android.net.Uri
import com.emikhalets.simpleevents.domain.repository.AppRepository
import javax.inject.Inject

class ImportEventsUseCase @Inject constructor(
    private val appRepository: AppRepository,
) {

    suspend operator fun invoke(uri: Uri) = appRepository.importEvents(uri)
}
