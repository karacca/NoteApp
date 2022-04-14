package com.task.noteapp.presentation.features.home.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.task.noteapp.R
import com.task.noteapp.domain.model.Note
import com.task.noteapp.presentation.theme.Dimens
import com.task.noteapp.presentation.theme.NoteAppTheme

/**
 * @author karacca
 * @date 13.03.2022
 */

@ExperimentalFoundationApi
@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    note: Note,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier.combinedClickable(
            onClick = { onClick?.invoke() },
            onLongClick = { onLongClick?.invoke() }
        ),
        backgroundColor = if (selected) {
            MaterialTheme.colors.primary
        } else {
            MaterialTheme.colors.background
        }
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(Dimens.Large)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = note.prettyCreatedDate,
                        style = MaterialTheme.typography.overline
                    )
                    if (note.modifiedDate != null) {
                        Spacer(modifier = Modifier.size(Dimens.Small))
                        Icon(
                            modifier = Modifier.size(Dimens.Medium),
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = stringResource(R.string.content_description)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(Dimens.Small))
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = note.description,
                    style = MaterialTheme.typography.caption,
                    maxLines = 2
                )
            }
            if (note.imageUrl != null) {
                AsyncImage(
                    modifier = Modifier
                        .width(128.dp)
                        .fillMaxHeight(),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(note.imageUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.img_placeholder),
                    contentDescription = stringResource(R.string.content_description),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
@ExperimentalFoundationApi
fun NoteCardLightPreview() {
    NoteAppTheme {
        NoteCard(note = Note.Mock)
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
@ExperimentalFoundationApi
fun NoteCardDarkPreview() {
    NoteAppTheme {
        NoteCard(note = Note.Mock)
    }
}
