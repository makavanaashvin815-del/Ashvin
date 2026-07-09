package com.example

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.database.AppDatabase
import com.example.data.entity.HomeActivityLog
import com.example.data.entity.HomeState
import com.example.data.entity.StudyTask
import com.example.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private lateinit var database: AppDatabase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        val context = ApplicationProvider.getApplicationContext<Application>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        viewModel = HomeViewModel(context)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInitialHomeStateCreation() = runTest {
        advanceUntilIdle()

        val state = viewModel.homeState.value
        assertNotNull(state)
        assertEquals("Alexander", state.userName)
        assertEquals("Learn Jetpack Compose Basics", state.currentGoal)
        assertEquals(3, state.studyStreak)
        assertFalse(state.studyModeActive)
    }

    @Test
    fun testSetChatInput() = runTest {
        val testMessage = "Umaa, help me with studies!"
        viewModel.setChatInput(testMessage)
        
        advanceUntilIdle()
        
        assertEquals(testMessage, viewModel.chatInput.value)
    }

    @Test
    fun testSendMessage() = runTest {
        val testMessage = "Hi Umaa, how are you?"
        viewModel.setChatInput(testMessage)
        viewModel.sendMessage()

        advanceUntilIdle()

        assertEquals("", viewModel.chatInput.value)
        val logs = viewModel.activityLogs.value
        assertTrue(logs.isNotEmpty())
    }

    @Test
    fun testSetGoal() = runTest {
        val newGoal = "Master Kotlin Coroutines"
        viewModel.setGoal(newGoal)

        advanceUntilIdle()

        val state = viewModel.homeState.value
        assertEquals(newGoal, state.currentGoal)
    }

    @Test
    fun testChangeUserName() = runTest {
        val newName = "Priya"
        viewModel.changeUserName(newName)

        advanceUntilIdle()

        val state = viewModel.homeState.value
        assertEquals(newName, state.userName)
    }

    @Test
    fun testActivateStudyMode() = runTest {
        assertFalse(viewModel.homeState.value.studyModeActive)

        viewModel.activateStudyMode()
        advanceUntilIdle()

        assertTrue(viewModel.homeState.value.studyModeActive)
    }

    @Test
    fun testDeactivateStudyMode() = runTest {
        viewModel.activateStudyMode()
        advanceUntilIdle()
        assertTrue(viewModel.homeState.value.studyModeActive)

        viewModel.deactivateStudyMode()
        advanceUntilIdle()

        assertFalse(viewModel.homeState.value.studyModeActive)
        assertTrue(viewModel.homeState.value.studyStreak > 3)
    }

    @Test
    fun testMakeExcuse_Reels() = runTest {
        viewModel.makeExcuse("Reels")
        advanceUntilIdle()

        val logs = viewModel.activityLogs.value
        assertTrue(logs.any { it.message.contains("Reels") && it.isUser })
        assertTrue(logs.any { it.message.contains("Bestie ❤️") && !it.isUser })
    }

    @Test
    fun testMakeExcuse_Neend() = runTest {
        viewModel.makeExcuse("Neend")
        advanceUntilIdle()

        val logs = viewModel.activityLogs.value
        assertTrue(logs.any { it.message.contains("neend") && it.isUser })
        assertTrue(logs.any { it.message.contains("😤") && !it.isUser })
    }

    @Test
    fun testAddStudyTask() = runTest {
        val taskTitle = "Complete Chapter 5"
        viewModel.addStudyTask(taskTitle)

        advanceUntilIdle()

        val tasks = viewModel.studyTasks.value
        assertTrue(tasks.any { it.title == taskTitle })
    }

    @Test
    fun testToggleStudyTask() = runTest {
        val taskTitle = "Solve 10 problems"
        viewModel.addStudyTask(taskTitle)
        advanceUntilIdle()

        val task = viewModel.studyTasks.value.first { it.title == taskTitle }
        assertFalse(task.isCompleted)

        viewModel.toggleStudyTask(task)
        advanceUntilIdle()

        val updatedTask = viewModel.studyTasks.value.first { it.title == taskTitle }
        assertTrue(updatedTask.isCompleted)
    }

    @Test
    fun testDeleteStudyTask() = runTest {
        val taskTitle = "Delete me"
        viewModel.addStudyTask(taskTitle)
        advanceUntilIdle()

        val task = viewModel.studyTasks.value.first { it.title == taskTitle }
        viewModel.deleteStudyTask(task)
        advanceUntilIdle()

        val tasks = viewModel.studyTasks.value
        assertFalse(tasks.any { it.title == taskTitle })
    }

    @Test
    fun testWipeConversationHistory() = runTest {
        viewModel.setChatInput("Test message")
        viewModel.sendMessage()
        advanceUntilIdle()

        val initialLogsCount = viewModel.activityLogs.value.size
        assertTrue(initialLogsCount > 0)

        viewModel.wipeConversationHistory()
        advanceUntilIdle()

        val logs = viewModel.activityLogs.value
        assertTrue(logs.any { it.message.contains("fresh start") })
    }

    @Test
    fun testIsTypingState() = runTest {
        assertFalse(viewModel.isTyping.value)

        viewModel.setChatInput("Test")
        viewModel.sendMessage()

        assertTrue(viewModel.isTyping.value)

        advanceUntilIdle()

        assertFalse(viewModel.isTyping.value)
    }

    @Test
    fun testMultipleExcusesZiddiResponse() = runTest {
        val excuses = listOf("Reels", "Neend", "Kitchen", "Dost", "Lazy")

        for (excuse in excuses) {
            viewModel.makeExcuse(excuse)
            advanceUntilIdle()
        }

        val logs = viewModel.activityLogs.value
        val umaResponses = logs.filter { !it.isUser && it.message.contains("Bestie ❤️") }
        assertTrue(umaResponses.size >= excuses.size)
    }

    @Test
    fun testStudySessionIncrementsStats() = runTest {
        val initialSessions = viewModel.homeState.value.totalSessions

        viewModel.activateStudyMode()
        advanceUntilIdle()
        viewModel.deactivateStudyMode()
        advanceUntilIdle()

        val updatedSessions = viewModel.homeState.value.totalSessions
        assertTrue(updatedSessions > initialSessions)
    }

    @Test
    fun testEmptyInputHandling() = runTest {
        viewModel.setChatInput("")
        viewModel.sendMessage()
        advanceUntilIdle()

        val logs = viewModel.activityLogs.value
        assertFalse(logs.any { it.message.isEmpty() && it.isUser })
    }

    @Test
    fun testEmptyGoalHandling() = runTest {
        val currentGoal = viewModel.homeState.value.currentGoal
        viewModel.setGoal("")
        advanceUntilIdle()

        assertEquals(currentGoal, viewModel.homeState.value.currentGoal)
    }

    @Test
    fun testEmptyNameHandling() = runTest {
        val currentName = viewModel.homeState.value.userName
        viewModel.changeUserName("   ")
        advanceUntilIdle()

        assertEquals(currentName, viewModel.homeState.value.userName)
    }
}
