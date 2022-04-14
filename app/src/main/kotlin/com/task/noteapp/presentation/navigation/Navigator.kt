package com.task.noteapp.presentation.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.task.noteapp.presentation.features.home.HomeScreen
import com.task.noteapp.presentation.features.note.NoteScreen

/**
 * @author karacca
 * @date 14.03.2022
 */

@ExperimentalFoundationApi
@Composable
fun Navigator() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Screen.Note.route) {
            NoteScreen(navController = navController)
        }

        composable(
            route = "${Screen.Note.route}?noteId={noteId}",
            arguments = listOf(
                navArgument(name = "noteId") {
                    type = NavType.IntType
                }
            )
        ) {
            NoteScreen(navController = navController)
        }
    }
}
