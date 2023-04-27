package com.emikhalets.simpleevents.domain.usecase.groups

import com.emikhalets.simpleevents.domain.entity.GroupEntity
import com.emikhalets.simpleevents.domain.repository.DatabaseRepository
import javax.inject.Inject

class DeleteGroupUseCase @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) {

    suspend operator fun invoke(entity: GroupEntity) = databaseRepo.deleteGroup(entity)
}
