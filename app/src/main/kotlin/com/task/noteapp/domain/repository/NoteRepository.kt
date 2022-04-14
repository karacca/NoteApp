package com.task.noteapp.domain.repository

import com.task.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * @author karacca
 * @date 13.03.2022
 */

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNote(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}
