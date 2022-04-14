package com.task.noteapp.presentation.features.note

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.task.noteapp.R
import com.task.noteapp.presentation.theme.Dimens
import com.task.noteapp.utils.TestTags
import kotlinx.coroutines.flow.collectLatest

/**
 * @author karacca
 * @date 13.03.2022
 */

@Composable
fun NoteScreen(
    viewModel: NoteViewModel = hiltViewModel(),
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    val viewState = viewModel.state.value

    LaunchedEffect(key1 = "") {
        viewModel.sideEffect.collectLatest {
            when (it) {
                NoteViewModel.Effect.PopBackStack -> {
                    navController.popBackStack()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                title = { Text(text = stringResource(id = R.string.title_note_detail)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(
                                id = R.string.content_description
                            ),
                        )
                    }
                }
            )
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier.padding(Dimens.Large),
            verticalArrangement = Arrangement.spacedBy(Dimens.Large)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().testTag(TestTags.NOTE_TITLE),
                label = { Text(text = stringResource(id = R.string.hint_note_title)) },
                singleLine = true,
                value = viewState.note.title,
                onValueChange = { viewModel.dispatch(NoteViewModel.Action.UpdateTitle(it)) }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().testTag(TestTags.NOTE_IMAGE_URL),
                label = { Text(text = stringResource(id = R.string.hint_note_image_url)) },
                singleLine = true,
                value = viewState.note.imageUrl ?: "",
                onValueChange = { viewModel.dispatch(NoteViewModel.Action.UpdateImageUrl(it)) }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().testTag(TestTags.NOTE_DESCRIPTION),
                label = { Text(text = stringResource(id = R.string.hint_note_description)) },
                value = viewState.note.description,
                onValueChange = { viewModel.dispatch(NoteViewModel.Action.UpdateDescription(it)) }
            )

            Button(
                modifier = Modifier.fillMaxWidth().testTag(TestTags.SAVE_NOTE),
                enabled = viewState.isNoteValid,
                onClick = { viewModel.dispatch(NoteViewModel.Action.SaveNote) }
            ) {
                Text(text = stringResource(id = R.string.action_save_note))
            }
        }
    }
}
