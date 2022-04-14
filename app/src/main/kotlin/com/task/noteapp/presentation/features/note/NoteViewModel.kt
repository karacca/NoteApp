package com.task.noteapp.presentation.features.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.task.noteapp.domain.interactor.GetNote
import com.task.noteapp.domain.interactor.InsertNote
import com.task.noteapp.domain.model.Note
import com.task.noteapp.presentation.core.BaseViewModel
import com.task.noteapp.presentation.core.SideEffect
import com.task.noteapp.presentation.core.ViewAction
import com.task.noteapp.presentation.core.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author karacca
 * @date 13.03.2022
 */

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getNote: GetNote,
    private val insertNote: InsertNote,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<NoteViewModel.State, NoteViewModel.Action, NoteViewModel.Effect>(
    initialState = State(
        note = Note(
            title = "",
            description = "",
            imageUrl = "",
            createdDate = System.currentTimeMillis()
        )
    )
) {

    init {
        val noteId = savedStateHandle.get<Int>("noteId")
        if (noteId != null) {
            fetchNote(noteId)
        }
    }

    override fun dispatch(action: Action) {
        when (action) {
            is Action.Data -> {
                updateState(
                    state.value.copy(
                        note = action.note.copy(
                            modifiedDate = System.currentTimeMillis()
                        )
                    )
                )
            }

            is Action.SaveNote -> {
                viewModelScope.launch {
                    updateState(state.value.copy(loading = true))
                    val note = state.value.note
                    insertNote.invoke(note)
                    updateState(state.value.copy(loading = false))
                    updateSideEffect(Effect.PopBackStack)
                }
            }

            is Action.UpdateTitle -> {
                val note = state.value.note.copy(title = action.content)
                updateState(state.value.copy(note = note))
            }

            is Action.UpdateDescription -> {
                val note = state.value.note.copy(description = action.content)
                updateState(state.value.copy(note = note))
            }

            is Action.UpdateImageUrl -> {
                val note = state.value.note.copy(imageUrl = action.content)
                updateState(state.value.copy(note = note))
            }
        }
    }

    private fun fetchNote(id: Int) {
        viewModelScope.launch {
            updateState(state.value.copy(loading = true))
            val note = getNote(id)
            if (note != null) {
                dispatch(Action.Data(note))
            } else {
                updateState(state.value.copy(loading = false))
            }
        }
    }

    data class State(
        val loading: Boolean = false,
        val note: Note
    ) : ViewState {

        val isNoteValid: Boolean
            get() = note.title.isNotEmpty() && note.description.isNotEmpty()
    }

    sealed class Action : ViewAction {
        data class Data(val note: Note) : Action()
        object SaveNote : Action()
        data class UpdateTitle(val content: String) : Action()
        data class UpdateImageUrl(val content: String) : Action()
        data class UpdateDescription(val content: String) : Action()
    }

    sealed class Effect : SideEffect {
        object PopBackStack : Effect()
    }
}
