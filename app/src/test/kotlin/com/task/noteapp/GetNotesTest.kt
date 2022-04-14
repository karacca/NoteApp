package com.task.noteapp

import com.task.noteapp.domain.interactor.GetNotes
import com.task.noteapp.domain.interactor.InsertNote
import com.task.noteapp.domain.model.Note
import com.task.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

/**
 * @author karacca
 * @date 14.03.2022
 */

class GetNotesTest {

    private lateinit var getNotes: GetNotes
    private lateinit var insertNote: InsertNote

    private lateinit var repository: NoteRepository

    @Before
    fun setup() {
        repository = FakeRepository()
        getNotes = GetNotes(repository)
        insertNote = InsertNote(repository)

        val notes = arrayListOf<Note>()
        for (i in 0..100) {
            notes.add(
                Note(
                    title = "",
                    description = "",
                    imageUrl = "",
                    createdDate = Random().nextLong()
                )
            )
        }

        runBlocking { notes.forEach { insertNote(it) } }
    }

    @Test
    fun `Get notes with correct order`() {
        runBlocking {
            val notes = getNotes().first()
            val correctlySorted = notes.sortedByDescending { it.createdDate }
            assertEquals(notes, correctlySorted)
        }
    }
}
