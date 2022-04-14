package com.task.noteapp.presentation.features.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.task.noteapp.R
import com.task.noteapp.presentation.features.home.components.HomeTopAppBar
import com.task.noteapp.presentation.features.home.components.NoteCard
import com.task.noteapp.presentation.navigation.Screen
import com.task.noteapp.presentation.theme.Dimens
import com.task.noteapp.utils.TestTags

/**
 * @author karacca
 * @date 13.03.2022
 */

@Composable
@ExperimentalFoundationApi
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    val viewState = viewModel.state.value

    val message = stringResource(id = R.string.long_press_message)
    LaunchedEffect(scaffoldState.snackbarHostState) {
        scaffoldState.snackbarHostState.showSnackbar(
            message = message
        )
    }

    Scaffold(
        topBar = {
            HomeTopAppBar(
                homeState = viewState,
                onDeleteClick = { viewModel.dispatch(HomeViewModel.Action.DeleteNotes) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.testTag(TestTags.ADD_NOTE),
                onClick = { navController.navigate(Screen.Note.route) },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.content_description)
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        when {
            viewState.loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }

            viewState.notes.isNotEmpty() -> {
                LazyColumn(contentPadding = PaddingValues(top = Dimens.Large)) {
                    items(viewState.notes) {
                        NoteCard(
                            modifier = Modifier.padding(horizontal = Dimens.Large),
                            note = it,
                            selected = viewState.selectedNotes.contains(it),
                            onClick = {
                                if (viewState.selectedNotes.isNotEmpty()) {
                                    viewModel.dispatch(HomeViewModel.Action.SelectNote(it))
                                } else {
                                    navController.navigate(Screen.Note.route + "?noteId=${it.id}")
                                }
                            },
                            onLongClick = {
                                viewModel.dispatch(HomeViewModel.Action.SelectNote(it))
                            }
                        )
                        Spacer(modifier = Modifier.height(Dimens.Large))
                    }
                }
            }
        }
    }
}
