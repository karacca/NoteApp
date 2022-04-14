package com.task.noteapp.data.repository

import com.task.noteapp.data.source.NoteDao
import com.task.noteapp.domain.model.Note
import com.task.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

/**
 * @author karacca
 * @date 13.03.2022
 */

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes()
    }

    override suspend fun getNote(id: Int): Note? {
        return noteDao.getNote(id)
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }
}
