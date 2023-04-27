package com.emikhalets.simpleevents.domain.usecase.groups

import com.emikhalets.simpleevents.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetGroupsUseCase @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) {

    suspend operator fun invoke() = databaseRepo.getAllGroups()

    suspend operator fun invoke(id: Long) = databaseRepo.getGroupById(id)
}
