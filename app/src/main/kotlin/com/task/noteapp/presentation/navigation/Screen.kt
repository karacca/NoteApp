package com.task.noteapp.presentation.navigation

/**
 * @author karacca
 * @date 14.03.2022
 */

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Note : Screen("note")
}
