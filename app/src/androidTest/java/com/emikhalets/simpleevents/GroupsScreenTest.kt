package com.emikhalets.simpleevents

import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.emikhalets.simpleevents.data.database.AppDatabase
import com.emikhalets.simpleevents.data.database.entity.GroupDb
import com.emikhalets.simpleevents.data.repository.DatabaseRepositoryImpl
import com.emikhalets.simpleevents.domain.usecase.groups.GetGroupsUseCase
import com.emikhalets.simpleevents.presentation.MainActivity
import com.emikhalets.simpleevents.presentation.screens.groups.GroupsScreen
import com.emikhalets.simpleevents.presentation.screens.groups.GroupsViewModel
import com.emikhalets.simpleevents.presentation.theme.AppTheme
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber

@RunWith(AndroidJUnit4::class)
class GroupsScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val database by lazy { AppDatabase.getInstance(composeRule.activity) }
    private val eventsDao by lazy { database.eventsDao }
    private val alarmsDao by lazy { database.alarmsDao }
    private val groupsDao by lazy { database.groupsDao }

    private val databaseRepo by lazy { DatabaseRepositoryImpl(eventsDao, alarmsDao, groupsDao) }
    private val getGroupsUseCase by lazy { GetGroupsUseCase(databaseRepo) }
    private val viewModel by lazy { GroupsViewModel(getGroupsUseCase) }

    @Test
    fun testEmptyGroups() {
        composeRule.apply {
            activity.setContent {
                val state by viewModel.state.collectAsState()
                AppTheme {
                    GroupsScreen(state = state, onAction = {})
                }
            }
            runBlocking {
                groupsDao.insert(GroupDb(0, "Group 1", true))
                Timber.d("Group name = ${groupsDao.getEntityById(1).name}")
            }

            composeRule.onNodeWithText("Group 1").assertExists()
        }
    }
}