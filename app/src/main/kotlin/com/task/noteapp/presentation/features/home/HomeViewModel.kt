package com.task.noteapp.presentation.features.home

import androidx.lifecycle.viewModelScope
import com.task.noteapp.domain.interactor.DeleteNote
import com.task.noteapp.domain.interactor.GetNotes
import com.task.noteapp.domain.model.Note
import com.task.noteapp.presentation.core.BaseViewModel
import com.task.noteapp.presentation.core.SideEffect
import com.task.noteapp.presentation.core.ViewAction
import com.task.noteapp.presentation.core.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author karacca
 * @date 13.03.2022
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNotes: GetNotes,
    private val deleteNote: DeleteNote
) : BaseViewModel<HomeViewModel.State, HomeViewModel.Action, HomeViewModel.Effect>(
    initialState = State()
) {

    init {
        fetchNotes()
    }

    override fun dispatch(action: Action) {
        when (action) {
            is Action.SelectNote -> {
                val selectedNotes = state.value.selectedNotes.toMutableList()
                val noteSelected = selectedNotes.contains(action.note)
                if (noteSelected) {
                    selectedNotes.remove(action.note)
                } else {
                    selectedNotes.add(action.note)
                }
                updateState(state.value.copy(selectedNotes = selectedNotes))
            }

            Action.DeleteNotes -> viewModelScope.launch {
                updateState(state.value.copy(loading = true))
                val notes = state.value.selectedNotes
                val remaining = notes.toMutableList()
                notes.forEach {
                    deleteNote.invoke(it)
                    remaining.remove(it)
                }
                updateState(
                    state.value.copy(
                        loading = false,
                        notes = remaining,
                        selectedNotes = arrayListOf()
                    )
                )
            }

            is Action.Data -> {
                updateState(
                    state.value.copy(
                        loading = false,
                        notes = action.notes
                    )
                )
            }
        }
    }

    private fun fetchNotes() {
        updateState(state.value.copy(loading = true))
        getNotes().onEach {
            dispatch(Action.Data(it))
        }.launchIn(viewModelScope)
    }

    data class State(
        val loading: Boolean = false,
        val notes: List<Note> = emptyList(),
        val selectedNotes: List<Note> = emptyList()
    ) : ViewState

    sealed class Action : ViewAction {
        data class Data(val notes: List<Note>) : Action()
        data class SelectNote(val note: Note) : Action()
        object DeleteNotes : Action()
    }

    sealed class Effect : SideEffect
}
