package com.task.noteapp.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.task.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * @author karacca
 * @date 13.03.2022
 */

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNote(id: Int): Note?

    @Insert(onConflict = REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}
