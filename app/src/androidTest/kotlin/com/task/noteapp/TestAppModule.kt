package com.task.noteapp

import android.app.Application
import androidx.room.Room
import com.task.noteapp.data.repository.NoteRepositoryImpl
import com.task.noteapp.data.source.NoteDatabase
import com.task.noteapp.domain.interactor.DeleteNote
import com.task.noteapp.domain.interactor.GetNote
import com.task.noteapp.domain.interactor.GetNotes
import com.task.noteapp.domain.interactor.InsertNote
import com.task.noteapp.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author karacca
 * @date 14.03.2022
 */

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            NoteDatabase::class.java
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(database: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(database.dao)
    }

    @Provides
    @Singleton
    fun provideGetNotes(repository: NoteRepository) = GetNotes(repository)

    @Provides
    @Singleton
    fun provideGetNote(repository: NoteRepository) = GetNote(repository)

    @Provides
    @Singleton
    fun provideInsertNote(repository: NoteRepository) = InsertNote(repository)

    @Provides
    @Singleton
    fun provideDeleteNote(repository: NoteRepository) = DeleteNote(repository)
}
