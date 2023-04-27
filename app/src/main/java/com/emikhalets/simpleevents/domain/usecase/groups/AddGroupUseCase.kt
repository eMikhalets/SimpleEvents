package com.emikhalets.simpleevents.domain.usecase.groups

import com.emikhalets.simpleevents.domain.entity.GroupEntity
import com.emikhalets.simpleevents.domain.repository.DatabaseRepository
import javax.inject.Inject

class AddGroupUseCase @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) {

    suspend operator fun invoke(entity: GroupEntity) = when (entity.id) {
        0L -> databaseRepo.insertGroup(entity)
        else -> databaseRepo.updateGroup(entity)
    }
}
