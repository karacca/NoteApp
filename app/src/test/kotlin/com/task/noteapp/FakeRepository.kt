package com.task.noteapp

import com.task.noteapp.domain.model.Note
import com.task.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.flow

/**
 * @author karacca
 * @date 14.03.2022
 */

class FakeRepository : NoteRepository {

    private val notes = arrayListOf<Note>()

    override fun getNotes() = flow { emit(notes) }

    override suspend fun getNote(id: Int) = notes.find { it.id == id }

    override suspend fun insertNote(note: Note) {
        notes.add(note)
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }
}
