package com.task.noteapp.presentation.features.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.task.noteapp.R
import com.task.noteapp.presentation.features.home.HomeViewModel
import com.task.noteapp.presentation.theme.Dimens

/**
 * @author karacca
 * @date 14.03.2022
 */

@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    homeState: HomeViewModel.State,
    onDeleteClick: (() -> Unit)? = null
) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.primary,
        title = {
            Text(
                text = when {
                    homeState.loading -> {
                        stringResource(id = R.string.title_home_loading)
                    }

                    homeState.notes.isEmpty() -> {
                        stringResource(id = R.string.title_home_empty)
                    }

                    homeState.selectedNotes.isNotEmpty() -> {
                        stringResource(
                            id = R.string.title_home_selection,
                            homeState.selectedNotes.size
                        )
                    }

                    else -> {
                        stringResource(
                            id = R.string.title_home,
                            homeState.notes.size
                        )
                    }
                }
            )
        },
        actions = {
            if (homeState.selectedNotes.isNotEmpty()) {
                Icon(
                    modifier = Modifier
                        .padding(horizontal = Dimens.Medium)
                        .clickable { onDeleteClick?.invoke() },
                    imageVector = Icons.Default.Delete,
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = stringResource(
                        id = R.string.content_description
                    )
                )
            }
        }
    )
}
