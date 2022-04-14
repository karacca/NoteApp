package com.task.noteapp.domain.interactor

import com.task.noteapp.domain.repository.NoteRepository

/**
 * @author karacca
 * @date 14.03.2022
 */

class GetNote(private val repository: NoteRepository) {

    suspend operator fun invoke(id: Int) = repository.getNote(id)
}
