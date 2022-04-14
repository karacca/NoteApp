package com.task.noteapp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.task.noteapp.di.AppModule
import com.task.noteapp.presentation.MainActivity
import com.task.noteapp.presentation.navigation.Navigator
import com.task.noteapp.presentation.theme.NoteAppTheme
import com.task.noteapp.utils.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author karacca
 * @date 14.03.2022
 */

@ExperimentalFoundationApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class NoteAppTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
        composeRule.setContent {
            NoteAppTheme { Navigator() }
        }
    }

    @Test
    fun saveNewNote_displayAfter() {
        // Click FAB
        composeRule.onNodeWithTag(TestTags.ADD_NOTE).performClick()

        // Perform text inputs
        composeRule.onNodeWithTag(TestTags.NOTE_TITLE).performTextInput("Title")
        composeRule.onNodeWithTag(TestTags.NOTE_IMAGE_URL)
            .performTextInput("https://picsum.photos/600")
        composeRule.onNodeWithTag(TestTags.NOTE_DESCRIPTION).performTextInput("Description")
        composeRule.onNodeWithTag(TestTags.SAVE_NOTE).performClick()

        // Assert
        composeRule.onNodeWithText("Title").assertIsDisplayed()
    }
}
