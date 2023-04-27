package com.emikhalets.simpleevents.domain.usecase.events

import com.emikhalets.simpleevents.domain.entity.GroupEntity
import com.emikhalets.simpleevents.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetEventByGroupUseCase @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) {

    suspend operator fun invoke(entity: GroupEntity) = databaseRepo.getEventsByGroup(entity)
}
