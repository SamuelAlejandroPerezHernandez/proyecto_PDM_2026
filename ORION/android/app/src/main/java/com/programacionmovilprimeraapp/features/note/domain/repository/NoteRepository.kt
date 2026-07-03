package com.programacionmovilprimeraapp.features.note.domain.repository

import com.programacionmovilprimeraapp.features.note.domain.model.NoteModel

interface NoteRepository {
    suspend fun getNotes(): Result<List<NoteModel>>
    suspend fun addNote(title: String, content: String, categoryId: String?): Result<Unit>

    suspend fun deleteNote(id: String): Result<Unit>
}